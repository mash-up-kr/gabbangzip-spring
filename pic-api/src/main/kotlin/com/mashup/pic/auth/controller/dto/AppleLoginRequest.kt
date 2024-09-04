package com.mashup.pic.auth.controller.dto

import com.mashup.pic.auth.applicationService.dto.AppleLoginServiceRequest
import com.mashup.pic.domain.user.LoginProvider
import jakarta.validation.constraints.NotBlank

data class AppleLoginRequest(
    @NotBlank val idToken: String,
    @NotBlank val user: String,
    val fullName: String?
) {
    fun toServiceRequest(): AppleLoginServiceRequest {
        return AppleLoginServiceRequest(
            idToken = idToken,
            provider = LoginProvider.APPLE,
            fullName = fullName ?: "Pic User",
            user = user
        )
    }
}
