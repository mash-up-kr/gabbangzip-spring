package com.mashup.pic.auth.controller.dto

import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
        @field:NotBlank val token: String,
        @field:NotBlank val provider: String
) {
    fun toServiceRequest(): LoginServiceRequest {
        return LoginServiceRequest(token, provider)
    }
}
