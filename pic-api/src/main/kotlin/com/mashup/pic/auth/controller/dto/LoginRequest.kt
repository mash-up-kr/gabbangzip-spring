package com.mashup.pic.auth.controller.dto

import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest

data class LoginRequest(
        val kakaoAccessToken: String
) {
    fun toServiceRequest(): LoginServiceRequest {
        return LoginServiceRequest(kakaoAccessToken)
    }
}
