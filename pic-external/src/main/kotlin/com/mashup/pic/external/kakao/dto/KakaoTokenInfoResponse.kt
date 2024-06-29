package com.mashup.pic.external.kakao.dto

data class KakaoTokenInfoResponse(
    val id: Long,
    val expiresIn: Int,
    val appId: Int
)
