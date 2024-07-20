package com.mashup.pic.domain.group

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
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
        val user =
            userRepository.findByIdOrNull(userId) ?: throw PicException.of(
                type = PicExceptionType.NOT_EXIST,
                message = "$userId 에 해당하는 유저를 찾을 수 없습니다."
            )
        val group =
            groupRepository.findByIdOrNull(groupId) ?: throw PicException.of(
                type = PicExceptionType.NOT_EXIST,
                message = "$groupId 에 해당하는 그룹을 찾을 수 없습니다."
            )
        return groupJoinRepository.save(GroupJoin(user, group)).toDto()
    }
}
