package com.mashup.pic.fake

import com.mashup.pic.external.common.JwksClient
import com.mashup.pic.external.common.response.JwksResponse
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class FakeJwksClient : JwksClient {
    override fun getJwks(): JwksResponse {
        return TODO()
    }

    override fun refreshAndGetJwks(): JwksResponse {
        return TODO()
    }

    override fun getOAuthId(code: String): Long {
        return TODO()
    }
}
