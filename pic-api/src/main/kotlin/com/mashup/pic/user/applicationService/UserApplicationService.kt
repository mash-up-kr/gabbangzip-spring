package com.mashup.pic.user.applicationService

import com.mashup.pic.domain.user.UserService
import com.mashup.pic.external.kakao.KakaoClient
import com.mashup.pic.security.authentication.UserInfo
import com.mashup.pic.security.jwt.JwtManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserApplicationService(
    private val userService: UserService,
    private val kakaoClient: KakaoClient,
    private val jwtManager: JwtManager
) {
    fun callbackPage(code: String): String? {
        val oAuthId = kakaoClient.getOAuthId(code)
        val user = userService.findUserByOAuthIdOrNull(oAuthId) ?: return null
        return jwtManager.generateAuthToken(
            UserInfo(
                id = user.id,
                nickname = user.nickname,
                roles = user.roles
            )
        ).accessToken
    }

    @Transactional
    fun deleteUser(userId: Long): Long {
        userService.deleteUser(userId)
        return userId
    }
}
