package com.mashup.pic.external.kakao

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.external.common.JwksClient
import com.mashup.pic.external.common.response.JwksResponse
import com.mashup.pic.external.kakao.dto.KakaoTokenInfoResponse
import com.mashup.pic.external.kakao.dto.KakaoTokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoClient(
    private val restClient: RestClient,
    @Value("\${kakao.jwk-uri}") private val jwkUri: String,
    @Value("\${kakao.token-uri}") private val tokenUri: String,
    @Value("\${kakao.info-uri}") private val infoUri: String,
    @Value("\${kakao.audience.rest}") private val restApiKey: String,
    @Value("\${kakao.redirect.uri}") private val redirectUri: String,
) : JwksClient {
    @Cacheable("kakao-jwks")
    override fun getJwks(): JwksResponse {
        return requestJwks()
    }

    @CachePut("kakao-jwks")
    override fun refreshAndGetJwks(): JwksResponse {
        return requestJwks()
    }

    fun getOAuthId(code: String): Long {
        val tokenResponse = requestToken(code)
        return requestTokenInfo(tokenResponse.accessToken).id
    }

    private fun requestTokenInfo(accessToken: String): KakaoTokenInfoResponse {
        return restClient.get()
            .uri(infoUri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw PicException.of(
                    PicExceptionType.EXTERNAL_COMMUNICATION_FAILURE,
                    "Error fetching JWKS: ${response.statusCode}",
                )
            }
            .body<KakaoTokenInfoResponse>()!!
    }

    private fun requestToken(code: String): KakaoTokenResponse {
        return restClient.post()
            .uri(tokenUri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(createTokenRequestBody(code))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw PicException.of(
                    PicExceptionType.EXTERNAL_COMMUNICATION_FAILURE,
                    "Error requesting access token: ${response.statusCode}",
                )
            }
            .body<KakaoTokenResponse>()!!
    }

    private fun requestJwks(): JwksResponse {
        return restClient.get()
            .uri(jwkUri)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw PicException.of(
                    PicExceptionType.EXTERNAL_COMMUNICATION_FAILURE,
                    "Error fetching JWKS: ${response.statusCode}",
                )
            }
            .body<JwksResponse>()!!
    }

    private fun createTokenRequestBody(code: String): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", restApiKey)
            add("redirect_uri", redirectUri)
            add("code", code)
        }
    }
}
