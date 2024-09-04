package com.mashup.pic.domain.group

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.Event
import com.mashup.pic.domain.event.EventRepository
import com.mashup.pic.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GroupService(
    private val groupRepository: GroupRepository,
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val groupJoinRepository: GroupJoinRepository
) {
    @Transactional
    fun create(
        name: String,
        keyword: GroupKeyword,
        imageUrl: String
    ): GroupDto {
        val group = Group(name, keyword, imageUrl)
        return groupRepository.save(group).toDto()
    }

    @Transactional
    fun join(
        userId: Long,
        groupId: Long
    ): GroupJoinDto {
        validateUser(userId)
        validateGroup(groupId)
        validateGroupCapacity(groupId)
        checkAlreadyJoined(userId, groupId)

        return groupJoinRepository.save(GroupJoin(userId, groupId)).toDto()
    }

    fun getGroupsByUser(userId: Long): List<GroupDto> {
        val myGroupIds = groupJoinRepository.findAllByUserId(userId).map { it.groupId }
        return groupRepository.findAllById(myGroupIds).map { it.toDto() }
    }

    fun getGroupById(groupId: Long): GroupDto {
        return groupRepository.findByIdOrNull(groupId)?.toDto() ?: throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "없는 그룹 ID")
    }

    fun getGroupByEventId(eventId: Long): GroupKeyword {
        val event = getEventById(eventId)
        return getGroupById(event.groupId).keyword
    }

    fun getJoinedUserIds(groupId: Long): List<Long> {
        val groupJoins = groupJoinRepository.findAllByGroupId(groupId)
        return groupJoins.map { it.userId }
    }

    @Transactional
    fun withdraw(userId: Long, groupId: Long) {
        if (!groupJoinRepository.existsByUserIdAndGroupId(userId, groupId)) {
            throw PicException.of(PicExceptionType.NOT_EXIST, "참여하고 있는 그룹이 아닙니다")
        }

        groupJoinRepository.deleteByUserIdAndGroupId(userId, groupId)
    }

    private fun getEventById(eventId: Long): Event {
        return eventRepository.findByIdOrNull(eventId)
            ?: throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "없는 이벤트")
    }

    private fun validateUser(userId: Long) {
        if (!checkUserExists(userId)) {
            throw PicException.of(
                type = PicExceptionType.NOT_EXIST,
                message = "$userId 에 해당하는 유저를 찾을 수 없습니다."
            )
        }
    }

    private fun validateGroup(groupId: Long) {
        if (!checkGroupExists(groupId)) {
            throw PicException.of(
                type = PicExceptionType.NOT_EXIST,
                message = "$groupId 에 해당하는 그룹을 찾을 수 없습니다."
            )
        }
    }

    private fun validateGroupCapacity(groupId: Long) {
        if (isGroupFull(groupId)) {
            throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "인원 초과")
        }
    }

    private fun checkAlreadyJoined(
        userId: Long,
        groupId: Long
    ) {
        if (hadAlreadyJoined(userId, groupId)) {
            throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "이미 참여")
        }
    }

    private fun checkUserExists(userId: Long): Boolean {
        return userRepository.existsById(userId)
    }

    private fun checkGroupExists(groupId: Long): Boolean {
        return groupRepository.existsById(groupId)
    }

    private fun isGroupFull(groupId: Long): Boolean {
        return groupJoinRepository.findAllByGroupId(groupId).size >= GROUP_MEMBER_MAX_COUNT
    }

    private fun hadAlreadyJoined(
        userId: Long,
        groupId: Long
    ): Boolean {
        return groupJoinRepository.existsByUserIdAndGroupId(userId, groupId)
    }

    companion object {
        const val GROUP_MEMBER_MAX_COUNT = 4
    }
}
