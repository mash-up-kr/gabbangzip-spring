package com.mashup.pic.auth.controller.dto

import com.mashup.pic.domain.user.User
import com.mashup.pic.security.authentication.AuthToken

data class LoginResponse(
    val userId: Long,
    val nickname: String,
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun from(
            user: User,
            authToken: AuthToken,
        ): LoginResponse {
            return LoginResponse(
                userId = user.id,
                nickname = user.nickname,
                accessToken = authToken.accessToken,
                refreshToken = authToken.refreshToken,
            )
        }
    }
}
