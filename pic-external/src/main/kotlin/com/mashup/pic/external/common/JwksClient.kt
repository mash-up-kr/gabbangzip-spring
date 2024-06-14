package com.mashup.pic.external.common

import com.mashup.pic.external.common.response.JwksResponse

interface JwksClient {
    fun getJwks(): JwksResponse
    fun refreshAndGetJwks(): JwksResponse
}
