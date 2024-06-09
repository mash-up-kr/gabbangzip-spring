package com.mashup.pic.external.kakao.response

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfoResponse(
        val id: Long,
        @JsonProperty("connected_at") val connectedAt: String,
        val properties: UserProperties,
        @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount
)

data class UserProperties(
        val nickname: String
)

data class KakaoAccount(
        @JsonProperty("profile_nickname_needs_agreement") val profileNicknameNeedsAgreement: Boolean,
        val profile: KakaoProfile
)

data class KakaoProfile(
        val nickname: String,
        @JsonProperty("is_default_nickname") val isDefaultNickname: Boolean
)
