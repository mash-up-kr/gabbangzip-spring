package com.mashup.pic.group.applicationservice

import com.mashup.pic.domain.event.EventService
import com.mashup.pic.domain.event.EventStatus
import com.mashup.pic.domain.event.UploadService
import com.mashup.pic.domain.event.VoteService
import com.mashup.pic.domain.group.GroupDto
import com.mashup.pic.domain.group.GroupService
import com.mashup.pic.domain.result.ResultDto
import com.mashup.pic.domain.result.ResultService
import com.mashup.pic.group.applicationservice.dto.CreateGroupResponse
import com.mashup.pic.group.applicationservice.dto.CreateGroupServiceRequest
import com.mashup.pic.group.applicationservice.dto.JoinGroupServiceRequest
import com.mashup.pic.group.controller.dto.FramedImage
import com.mashup.pic.group.controller.dto.GroupViewStatus
import com.mashup.pic.group.controller.dto.JoinGroupResponse
import com.mashup.pic.group.controller.dto.RecentEvent
import com.mashup.pic.group.controller.dto.ViewGroupDetailResponse
import com.mashup.pic.group.controller.dto.ViewGroupItem
import com.mashup.pic.group.controller.dto.ViewGroupResponse
import com.mashup.pic.group.controller.dto.toViewGroupItem
import com.mashup.pic.group.util.GroupViewStatusUtil
import com.mashup.pic.util.InviteCodeUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class GroupApplicationService(
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

    fun joinGroup(request: JoinGroupServiceRequest): JoinGroupResponse {
        val groupId = InviteCodeUtil.getIdFromInviteCode(request.code)
        groupService.join(
            userId = request.userId,
            groupId = groupId
        )
        return JoinGroupResponse(groupId)
    }

    fun getAllGroups(userId: Long): ViewGroupResponse {
        val groups = groupService.getGroupsByUser(userId)
        val groupItems = groups.map { group -> getViewGroupItem(userId, group) }
        return ViewGroupResponse(groupItems)
    }

    fun getGroup(userId: Long, groupId: Long): ViewGroupDetailResponse? {
        // TODO: add detail view logic
        return null
    }

    private fun getViewGroupItem(userId: Long, group: GroupDto) : ViewGroupItem {
        val lastEvent = eventService.getLastEvent(group.id)

        val status: GroupViewStatus
        val statusDescription = GroupViewStatusUtil.generateDescription(lastEvent)
        var recentEvent = RecentEvent()
        val cardFrontImageUrl: String
        var cardBackImages: ResultDto? = null

        // 현 이벤트 X, 역대 이벤트 X
        if (lastEvent == null) {
            status = GroupViewStatus.NO_PAST_AND_CURRENT_EVENT
            cardFrontImageUrl = group.imageUrl
        }

        // 현 이벤트 X, 역대 이벤트 O
        else if (lastEvent.eventStatus == EventStatus.COMPLETE) {
            status = GroupViewStatus.EVENT_COMPLETED
            recentEvent = RecentEvent(lastEvent.description, lastEvent.date)
            cardBackImages = resultService.getResultOfEvent(lastEvent.id)
            cardFrontImageUrl = cardBackImages.resultImages[0].imageUrl
        }

        // 현 이벤트 O
        else {
            recentEvent = RecentEvent(lastEvent.description, lastEvent.date)
            cardFrontImageUrl = eventService.getRandomImageOption(lastEvent.id)

            status = when (lastEvent.eventStatus) {
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
                    GroupViewStatus.EVENT_COMPLETED
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

    private fun convertResultDtoToFramedImages(resultDto: ResultDto?): List<FramedImage>? {
        return resultDto?.resultImages?.map { resultItem ->
            FramedImage(
                imageUrl = resultItem.imageUrl,
                frame = resultItem.frame
            )
        }
    }
}
