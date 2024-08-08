package com.mashup.pic.domain.user

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {
    fun findUserByOAuthIdOrNull(oAuthId: Long): UserDto? {
        return userRepository.findByOAuthId(oAuthId)?.toDto() ?: return null
    }

    fun findUserByUserId(userId: Long): UserDto {
        return userRepository.findByIdOrNull(userId)?.toDto()
            ?: throw PicException.of(PicExceptionType.AUTH_ERROR)
    }

    @Transactional
    fun create(
        oAuthId: Long,
        nickname: String,
        profileImage: String
    ): UserDto {
        return userRepository.save(
            User(
                oAuthId = oAuthId,
                nickname = nickname,
                profileImage = profileImage
            )
        ).toDto()
    }

    @Transactional
    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun getUsersByIds(ids: List<Long>): List<UserDto> {
        return userRepository.findAllByIdIn(ids).map { it.toDto() }
    }
}
