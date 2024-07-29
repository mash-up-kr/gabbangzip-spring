package com.mashup.pic.domain.group

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.user.User
import com.mashup.pic.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GroupService(
    private val groupRepository: GroupRepository,
    private val groupJoinRepository: GroupJoinRepository
) {
    @Transactional
    fun create(
        name: String,
        keyword: GroupKeyword,
        imageUrl: String
    ): GroupDto {
        return groupRepository.save(Group(name, keyword, imageUrl)).toDto()
    }

    @Transactional
    fun join(
        userId: Long,
        groupId: Long
    ): GroupJoinDto {
        if (isGroupFull(groupId)) {
            throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "인원 초과")
        }

        if (isAlreadyJoined(userId, groupId)) {
            throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "이미 참여")
        }

        return groupJoinRepository.save(GroupJoin(userId, groupId)).toDto()
    }

    private fun isGroupFull(groupId: Long) : Boolean {
        return groupJoinRepository.findAllByGroupId(groupId).size >= GROUP_MEMBER_MAX_COUNT
    }

    private fun isAlreadyJoined(userId: Long, groupId: Long) : Boolean {
        return groupJoinRepository.existsByUserIdAndGroupId(userId, groupId)
    }

    companion object {
        const val GROUP_MEMBER_MAX_COUNT = 4
    }
}
