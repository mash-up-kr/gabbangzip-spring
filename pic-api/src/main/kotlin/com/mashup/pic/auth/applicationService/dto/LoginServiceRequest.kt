package com.mashup.pic.auth.applicationService.dto

data class LoginServiceRequest(
    val idToken: String,
    val provider: LoginProvider,
    val nickname: String,
    val profileImage: String,
)

enum class LoginProvider {
    KAKAO,
    NAVER,
    GOOGLE,
}
