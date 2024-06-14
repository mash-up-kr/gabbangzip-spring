package com.mashup.pic.security.oidc

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.external.common.response.JwkKey
import com.mashup.pic.external.kakao.KakaoJwksClient
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.Key
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*


@Component
class KakaoIdTokenValidator(
        private val kakaoJwksClient: KakaoJwksClient,
        private val objectMapper: ObjectMapper,
        @Value("\${kakao.issuer}") private val issuer: String,
        @Value("\${kakao.audience.rest}") private val restAudience: String,
        @Value("\${kakao.audience.native}") private val nativeAudience: String
) : IdTokenValidator {

    private val decoder = Base64.getUrlDecoder()
    private val keyFactory = KeyFactory.getInstance(SIGNING_ALGORITHM)

    override fun validateAndGetId(idToken: String, nickname: String): Long {
        verifyPayload(idToken, nickname)
        verifySignature(idToken)
        return extractSub(idToken).toLong()
    }

    private fun extractSub(idToken: String): String {
        val payload = decodePayload(idToken)
        return payload[SUB_KEY] as String? ?: throw Exception("SUB 없음")
    }

    private fun verifyPayload(idToken: String, nickname: String) {
        val payload = decodePayload(idToken)
        require(payload[ISSUER_KEY] == issuer) { "Invalid issuer" }
        require(payload[AUDIENCE_KEY] == restAudience || payload[AUDIENCE_KEY] == nativeAudience) { "Invalid audience" }
        require(payload[NICKNAME_KEY] == nickname) { "Invalid nickname" }
    }

    private fun verifySignature(idToken: String) {
        val kid = extractKid(idToken)
        val publicKey = getPublicKey(kid)
        Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(idToken)
    }

    private fun extractKid(idToken: String): String {
        val header = decodeHeader(idToken)
        return header[KID_KEY] as String? ?: throw Exception("KID 없음")
    }

    private fun getPublicKey(kid: String): Key {
        val jwk = getJwkByKid(kid)
        val n = BigInteger(1, decoder.decode(jwk.n))
        val e = BigInteger(1, decoder.decode(jwk.e))
        val keySpec = RSAPublicKeySpec(n, e)
        return keyFactory.generatePublic(keySpec)
    }

    private fun getJwkByKid(kid: String): JwkKey {
        return kakaoJwksClient.getJwks().getJwkKeyByKid(kid)
                ?: kakaoJwksClient.refreshAndGetJwks().getJwkKeyByKid(kid)
                ?: throw Exception("공개키를 가져올 수 없음")
    }

    private fun decodePayload(idToken: String): Map<String, Any> {
        val payload = idToken.split(".")[1]
        val decodedPayload = String(decoder.decode(payload))
        return objectMapper.readValue(decodedPayload, Map::class.java) as Map<String, Any>
    }

    private fun decodeHeader(idToken: String): Map<String, Any> {
        val header = idToken.split(".")[0]
        val decodedHeader = String(decoder.decode(header))
        return objectMapper.readValue(decodedHeader, Map::class.java) as Map<String, Any>
    }

    companion object {
        private const val ISSUER_KEY = "iss"
        private const val AUDIENCE_KEY = "aud"
        private const val NICKNAME_KEY = "nickname"
        private const val SUB_KEY = "sub"
        private const val KID_KEY = "kid"
        private const val SIGNING_ALGORITHM = "RSA"
    }
}
