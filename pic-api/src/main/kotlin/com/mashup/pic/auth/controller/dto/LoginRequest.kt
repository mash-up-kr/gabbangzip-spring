package com.mashup.pic.auth.controller.dto

import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.domain.user.LoginProvider
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @NotBlank val idToken: String,
    @NotBlank val provider: LoginProvider,
    @NotBlank val nickname: String,
    @NotBlank val profileImage: String
) {
    fun toServiceRequest(): LoginServiceRequest {
        return LoginServiceRequest(
            idToken = idToken,
            provider = provider,
            nickname = nickname,
            profileImage = profileImage
        )
    }
}
