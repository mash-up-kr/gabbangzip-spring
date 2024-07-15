package com.mashup.pic.fake

import com.mashup.pic.external.common.JwksClient
import com.mashup.pic.external.common.response.JwksResponse
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class FakeJwksClient : JwksClient {
    override fun getJwks(): JwksResponse {
        TODO("Do Nothing in testing environment")
    }

    override fun refreshAndGetJwks(): JwksResponse {
        TODO("Do Nothing in testing environment")
    }

    override fun getOAuthId(code: String): Long {
        TODO("Do Nothing in testing environment")
    }
}
