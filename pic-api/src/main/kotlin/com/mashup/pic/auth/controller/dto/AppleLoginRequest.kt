package com.mashup.pic.auth.controller.dto

import com.mashup.pic.auth.applicationService.dto.AppleLoginServiceRequest
import com.mashup.pic.domain.user.LoginProvider
import jakarta.validation.constraints.NotBlank

data class AppleLoginRequest(
    @NotBlank val idToken: String,
    @NotBlank val fullName: String,
    @NotBlank val user: String
) {
    fun toServiceRequest(): AppleLoginServiceRequest {
        return AppleLoginServiceRequest(
            idToken = idToken,
            provider = LoginProvider.APPLE,
            fullName = fullName,
            user = user
        )
    }
}
