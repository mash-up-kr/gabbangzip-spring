package com.mashup.pic.kakao.response

data class KakaoAccessTokenInfoResponse(
        val expiresInMillis: Long,
        val id: Long,
        val expiresIn: Int,
        val appId: Int
)

