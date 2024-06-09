package com.mashup.pic.auth.controller.dto

import com.mashup.pic.auth.applicationService.dto.LoginServiceResponse


data class LoginResponse(
        val userId: Long,
        val nickname: String,
        val accessToken: String,
        val refreshToken: String
) {
    companion object {
        fun from(loginServiceResponse: LoginServiceResponse): LoginResponse {
            return LoginResponse(
                    userId = loginServiceResponse.userId,
                    nickname = loginServiceResponse.nickname,
                    accessToken = loginServiceResponse.accessToken,
                    refreshToken = loginServiceResponse.refreshToken
            )
        }
    }
}
