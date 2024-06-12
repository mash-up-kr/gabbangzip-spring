package com.mashup.pic.external.kakao

import com.mashup.pic.external.kakao.response.KakaoAccessTokenInfoResponse
import com.mashup.pic.external.kakao.response.KakaoUserInfoResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class KakaoClient(
        private val restClient: RestClient
) {

    private val accessTokenInfoUri: String = "https://kapi.kakao.com/v1/user/access_token_info"
    private val userInfoUri: String = "https://kapi.kakao.com/v2/user/me"

    fun getAccessTokenPayload(accessToken: String): Long {
        return restClient.get()
                .uri(accessTokenInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "$BEARER_PREFIX$accessToken")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                    // TODO: throw Pic custom runtime exception
                }
                .body<KakaoAccessTokenInfoResponse>()!!.id
    }

    fun getUserInfo(accessToken: String): KakaoUserInfoResponse {
        return restClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "$BEARER_PREFIX$accessToken")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                    // TODO: throw Pic custom runtime exception
                }
                .body<KakaoUserInfoResponse>()?: throw Exception("Invalid Kakao AccessToken")
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
