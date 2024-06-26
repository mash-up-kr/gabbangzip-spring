package com.mashup.pic.user.applicationService

import com.mashup.pic.domain.user.UserService
import com.mashup.pic.external.kakao.KakaoClient
import org.springframework.stereotype.Service

@Service
class UserApplicationService(
    val userService: UserService,
    val kakaoClient: KakaoClient,
) {
    fun callbackPage(code: String): Long? {
        val oAuthId = kakaoClient.getOAuthId(code)
        return userService.findUserByOAuthIdOrNull(oAuthId)?.id
    }

    fun deleteUser(userId: Long) {
        userService.deleteUser(userId)
    }
}
