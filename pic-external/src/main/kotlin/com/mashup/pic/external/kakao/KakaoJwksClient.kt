package com.mashup.pic.external.kakao

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.external.common.JwksClient
import com.mashup.pic.external.common.response.JwksResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body


@Component
class KakaoJwksClient(
    private val restClient: RestClient,
    @Value("\${kakao.jwk-uri}") private val jwkUri: String
) : JwksClient {

    @Cacheable("kakao-jwks")
    override fun getJwks(): JwksResponse {
        return requestJwks()
    }

    @CachePut("kakao-jwks")
    override fun refreshAndGetJwks(): JwksResponse {
        return requestJwks()
    }

    private fun requestJwks(): JwksResponse {
        return restClient.get()
            .uri(jwkUri)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw PicException.of(PicExceptionType.EXTERNAL_COMMUNICATION_FAILURE, "Error fetching JWKS: ${response.statusCode}")
            }
            .body<JwksResponse>()!!
    }
}
