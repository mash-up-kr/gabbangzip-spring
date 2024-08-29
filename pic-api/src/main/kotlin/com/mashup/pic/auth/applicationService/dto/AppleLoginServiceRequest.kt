package com.mashup.pic.auth.applicationService.dto

import com.mashup.pic.domain.user.LoginProvider

data class AppleLoginServiceRequest(
    val idToken: String,
    val provider: LoginProvider,
    val fullName: String,
    val user: String
)
