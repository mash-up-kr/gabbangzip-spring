package com.mashup.pic.domain.user

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
