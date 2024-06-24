package com.mashup.pic.domain.user

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {
    fun findUserByOAuthIdOrNull(oAuthId: Long): User? {
        return userRepository.findByOAuthId(oAuthId)
    }

    fun findUserByUserId(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: throw PicException.of(PicExceptionType.AUTH_ERROR)
    }

    @Transactional
    fun create(
        oAuthId: Long,
        nickname: String,
        profileImage: String,
    ): User {
        return userRepository.save(
            User(
                oAuthId = oAuthId,
                nickname = nickname,
                profileImage = profileImage,
            ),
        )
    }
}
