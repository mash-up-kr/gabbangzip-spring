package com.mashup.pic.group.applicationservice

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.EventDto
import com.mashup.pic.domain.event.EventService
import com.mashup.pic.domain.event.EventStatus
import com.mashup.pic.domain.event.UploadService
import com.mashup.pic.domain.group.GroupDto
import com.mashup.pic.domain.group.GroupService
import com.mashup.pic.domain.result.ResultDto
import com.mashup.pic.domain.result.ResultService
import com.mashup.pic.domain.user.UserService
import com.mashup.pic.domain.vote.VoteService
import com.mashup.pic.group.applicationservice.dto.CreateGroupResponse
import com.mashup.pic.group.applicationservice.dto.CreateGroupServiceRequest
import com.mashup.pic.group.applicationservice.dto.JoinGroupServiceRequest
import com.mashup.pic.group.applicationservice.dto.WithdrawGroupServiceRequest
import com.mashup.pic.group.controller.dto.FramedImage
import com.mashup.pic.group.controller.dto.GroupMemberResponse
import com.mashup.pic.group.controller.dto.GroupViewStatus
import com.mashup.pic.group.controller.dto.HistoryItem
import com.mashup.pic.group.controller.dto.JoinGroupResponse
import com.mashup.pic.group.controller.dto.Member
import com.mashup.pic.group.controller.dto.RecentEvent
import com.mashup.pic.group.controller.dto.RecentEventDetail
import com.mashup.pic.group.controller.dto.ViewGroupDetailResponse
import com.mashup.pic.group.controller.dto.ViewGroupItem
import com.mashup.pic.group.controller.dto.ViewGroupResponse
import com.mashup.pic.group.controller.dto.WithdrawGroupResponse
import com.mashup.pic.group.controller.dto.toViewGroupItem
import com.mashup.pic.group.util.GroupViewStatusUtil
import com.mashup.pic.util.InviteCodeUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class GroupApplicationService(
    private val userService: UserService,
    private val groupService: GroupService,
    private val eventService: EventService,
    private val uploadService: UploadService,
    private val voteService: VoteService,
    private val resultService: ResultService
) {
    @Transactional
    fun create(request: CreateGroupServiceRequest): CreateGroupResponse {
        val groupDto = groupService.create(request.groupName, request.keyword, request.groupImageUrl)
        groupService.join(request.userId, groupDto.id)

        val invitationCode = InviteCodeUtil.generateInviteCode(groupDto.id)
        return CreateGroupResponse.from(groupDto, invitationCode)
    }

    @Transactional
    fun joinGroup(request: JoinGroupServiceRequest): JoinGroupResponse {
        val groupId = InviteCodeUtil.getIdFromInviteCode(request.code)
        groupService.join(
            userId = request.userId,
            groupId = groupId
        )
        val event = eventService.getLastEvent(groupId)
        if (event != null && event.eventStatus != EventStatus.COMPLETE) {
            eventService.joinEvent(request.userId, event.id)
        }
        return JoinGroupResponse(groupId)
    }

    fun getAllGroups(userId: Long): ViewGroupResponse {
        val groups = groupService.getGroupsByUser(userId).sortedByDescending { it.createdAt }
        val groupItems = groups.map { group -> getViewGroupItem(userId, group) }.sortedByDescending { it.recentEvent.date }
        return ViewGroupResponse(groupItems)
    }

    fun getGroupDetail(
        userId: Long,
        groupId: Long
    ): ViewGroupDetailResponse {
        val group = groupService.getGroupById(groupId)
        val lastEvent = eventService.getLastEvent(group.id)

        val status: GroupViewStatus
        val statusDescription = GroupViewStatusUtil.generateDescription(lastEvent)
        var recentEventDetail = RecentEventDetail()
        val cardFrontImageUrl: String
        var cardBackImages: ResultDto? = null

        if (lastEvent == null) { // 현 이벤트 X, 역대 이벤트 X
            status = GroupViewStatus.NO_PAST_AND_CURRENT_EVENT
            cardFrontImageUrl = group.imageUrl
        } else if (lastEvent.eventStatus == EventStatus.COMPLETE) { // 현 이벤트 X, 역대 이벤트 O
            status =
                runCatching {
                    if (eventService.hasVisitedEvent(userId, lastEvent.id)) {
                        GroupViewStatus.NO_CURRENT_EVENT
                    } else {
                        GroupViewStatus.EVENT_COMPLETED
                    }
                }.getOrDefault(GroupViewStatus.NO_CURRENT_EVENT)
            recentEventDetail = RecentEventDetail(lastEvent.id, lastEvent.description, lastEvent.date, getDeadline(lastEvent))
            cardBackImages = resultService.getResultOfEvent(lastEvent.id)
            cardFrontImageUrl = cardBackImages.resultImages[0].imageUrl
        } else { // 현 이벤트 O
            recentEventDetail = RecentEventDetail(lastEvent.id, lastEvent.description, lastEvent.date, getDeadline(lastEvent))
            cardFrontImageUrl = eventService.getRandomImageOptionFromEvent(lastEvent.id)

            status =
                when (lastEvent.eventStatus) {
                    EventStatus.UPLOADING ->
                        runCatching {
                            if (uploadService.hasUserUploaded(userId, lastEvent.id)) {
                                GroupViewStatus.AFTER_MY_UPLOAD
                            } else {
                                GroupViewStatus.BEFORE_MY_UPLOAD
                            }
                        }.getOrDefault(GroupViewStatus.AFTER_MY_UPLOAD)

                    EventStatus.VOTING ->
                        runCatching {
                            if (voteService.hasUserVoted(userId, lastEvent.id)) {
                                GroupViewStatus.AFTER_MY_VOTE
                            } else {
                                GroupViewStatus.BEFORE_MY_VOTE
                            } 
                        }.getOrDefault(GroupViewStatus.AFTER_MY_VOTE)

                    else -> {
                        GroupViewStatus.NO_CURRENT_EVENT
                    }
                }
        }

        return ViewGroupDetailResponse(
            id = groupId,
            name = group.name,
            keyword = group.keyword,
            status = status,
            statusDescription = statusDescription,
            recentEvent = recentEventDetail,
            cardFrontImageUrl = cardFrontImageUrl,
            cardBackImages = convertResultDtoToFramedImages(cardBackImages),
            history = getHistoryItems(groupId)
        )
    }

    fun getGroupMembers(groupId: Long): GroupMemberResponse {
        val joinedUserIds = groupService.getJoinedUserIds(groupId)
        val users = userService.getUsersByIds(joinedUserIds)

        val invitationCode = InviteCodeUtil.generateInviteCode(groupId)
        return GroupMemberResponse(
            members = users.map { Member(it.id, it.nickname) },
            invitationCode = invitationCode
        )
    }

    @Transactional
    fun withdrawGroup(request: WithdrawGroupServiceRequest): WithdrawGroupResponse {
        val lastEvent = eventService.getLastEvent(request.groupId)
        if (lastEvent != null && lastEvent.eventStatus != EventStatus.COMPLETE) {
            throw PicException.of(PicExceptionType.BAD_REQUEST, "이벤트가 진행 중이면 탈퇴할 수 없습니다")
        }

        groupService.withdraw(request.userId, request.groupId)

        return WithdrawGroupResponse(request.groupId)
    }

    private fun getViewGroupItem(
        userId: Long,
        group: GroupDto
    ): ViewGroupItem {
        val lastEvent = eventService.getLastEvent(group.id)

        val status: GroupViewStatus
        val statusDescription = GroupViewStatusUtil.generateDescription(lastEvent)
        var recentEvent = RecentEvent()
        val cardFrontImageUrl: String
        var cardBackImages: ResultDto? = null

        if (lastEvent == null) { // 현 이벤트 X, 역대 이벤트 X
            status = GroupViewStatus.NO_PAST_AND_CURRENT_EVENT
            cardFrontImageUrl = group.imageUrl
        } else if (lastEvent.eventStatus == EventStatus.COMPLETE) { // 현 이벤트 X, 역대 이벤트 O
            status = GroupViewStatus.NO_CURRENT_EVENT
            recentEvent = RecentEvent(lastEvent.id, lastEvent.description, lastEvent.date)
            cardBackImages = resultService.getResultOfEvent(lastEvent.id)
            cardFrontImageUrl = cardBackImages.resultImages[0].imageUrl
        } else { // 현 이벤트 O
            recentEvent = RecentEvent(lastEvent.id, lastEvent.description, lastEvent.date)
            cardFrontImageUrl = eventService.getRandomImageOptionFromEvent(lastEvent.id)

            status =
                when (lastEvent.eventStatus) {
                    EventStatus.UPLOADING ->
                        if (uploadService.hasUserUploaded(userId, lastEvent.id)) {
                            GroupViewStatus.AFTER_MY_UPLOAD
                        } else {
                            GroupViewStatus.BEFORE_MY_UPLOAD
                        }

                    EventStatus.VOTING ->
                        if (voteService.hasUserVoted(userId, lastEvent.id)) {
                            GroupViewStatus.AFTER_MY_VOTE
                        } else {
                            GroupViewStatus.BEFORE_MY_VOTE
                        }

                    else -> {
                        GroupViewStatus.NO_CURRENT_EVENT
                    }
                }
        }

        return group.toViewGroupItem(
            status = status,
            statusDescription = statusDescription,
            recentEvent = recentEvent,
            cardFrontImageUrl = cardFrontImageUrl,
            cardBackImages = convertResultDtoToFramedImages(cardBackImages)
        )
    }

    private fun getHistoryItems(groupId: Long): List<HistoryItem> {
        val events = eventService.getAllEventsAsc(groupId).drop(1)
        return events.map {
            HistoryItem(
                id = it.id,
                name = it.description,
                date = it.date,
                images = convertResultDtoToFramedImages(resultService.getResultOfEvent(it.id))
            )
        }
    }

    private fun getDeadline(event: EventDto): LocalDateTime {
        if (event.eventStatus == EventStatus.UPLOADING) {
            return event.createdAt.plusHours(UPLOADING_HOURS)
        }

        return event.uploadingEndDate?.plusHours(VOTING_HOURS) ?: throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "업로드 종료 시간 오류")
    }

    private fun convertResultDtoToFramedImages(resultDto: ResultDto?): List<FramedImage>? {
        return resultDto?.resultImages?.map { resultItem ->
            FramedImage(
                imageUrl = resultItem.imageUrl,
                frame = resultItem.frame
            )
        }
    }

    companion object {
        const val UPLOADING_HOURS = 2L
        const val VOTING_HOURS = 2L
    }
}
