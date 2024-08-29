package com.mashup.pic.auth.applicationService.dto

import com.mashup.pic.domain.user.LoginProvider

data class LoginServiceRequest(
    val idToken: String,
    val provider: LoginProvider,
    val nickname: String,
    val profileImage: String?
)
