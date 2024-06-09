package com.mashup.pic.auth.applicationService.dto

import com.mashup.pic.domain.user.User
import com.mashup.pic.security.authentication.AuthToken

data class LoginServiceResponse(
        val userId: Long,
        val nickname: String,
        val accessToken: String,
        val refreshToken: String
) {
    companion object {
        fun from(user: User, authToken: AuthToken): LoginServiceResponse {
            return LoginServiceResponse(
                    userId = user.id,
                    nickname = user.nickname,
                    accessToken = authToken.accessToken,
                    refreshToken = authToken.refreshToken
            )
        }
    }
}
