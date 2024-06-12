package com.mashup.pic.auth.applicationService.dto

data class LoginServiceRequest(
        val token: String,
        val provider: String
)
