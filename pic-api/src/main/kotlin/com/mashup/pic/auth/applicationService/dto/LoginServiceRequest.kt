package com.mashup.pic.auth.applicationService.dto

data class LoginServiceRequest(
        val idToken: String,
        val provider: String,
        val nickname: String,
        val profileImage: String
)
