package com.mashup.pic.kakao.response

data class KakaoUserInfoResponse(
        val id: Long,
        val connectedAt: String,
        val properties: UserProperties,
        val kakaoAccount: KakaoAccount
)

data class UserProperties(
        val nickname: String
)

data class KakaoAccount(
        val profileNicknameNeedsAgreement: Boolean,
        val profile: KakaoProfile
)

data class KakaoProfile(
        val nickname: String,
        val isDefaultNickname: Boolean
)
