package com.mashup.pic.auth.applicationService.dto

data class LoginServiceResponse (
        val userId: Long,
        val nickname: String,
        val accessToken: String,
        val refreshToken: String
)
