package com.mashup.pic.user.applicationService

import com.mashup.pic.domain.user.UserService
import com.mashup.pic.external.kakao.KakaoClient
import com.mashup.pic.security.authentication.UserInfo
import com.mashup.pic.security.jwt.JwtManager
import org.springframework.stereotype.Service

@Service
class UserApplicationService(
    val userService: UserService,
    val kakaoClient: KakaoClient,
    val jwtManager: JwtManager
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

    fun deleteUser(userId: Long) : Long {
        userService.deleteUser(userId)
        return userId
    }
}
