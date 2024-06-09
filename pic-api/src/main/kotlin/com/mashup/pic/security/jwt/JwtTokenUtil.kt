package com.mashup.pic.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.security.authentication.AuthToken
import com.mashup.pic.security.authentication.UserInfo
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtTokenUtil(
        @Value("\${jwt.secret-key}") private val secretKey: String,
        private val objectMapper: ObjectMapper
) {

    private val signKey: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    private val jwtParser: JwtParser = Jwts.parserBuilder().setSigningKey(signKey).build()

    fun generateAuthToken(userInfo: UserInfo): AuthToken {
        return AuthToken(
                accessToken = generateAccessToken(userInfo),
                refreshToken = generateRefreshToken()
        )
    }

    fun extractUserInfo(accessToken: String): UserInfo {
        return jwtParser.parseClaimsJws(accessToken).body?.let { claims ->
            objectMapper.convertValue(claims[CLAIM_USER_INFO_KEY], UserInfo::class.java)
        } ?: throw Exception() // TODO: replace to Pic Custom Exception
    }

    private fun generateAccessToken(userInfo: UserInfo): String {
        val now = Date()
        val expiration = Date(now.time + ACCESS_TOKEN_EXPIRATION)

        return Jwts.builder()
                .setSubject(userInfo.id.toString())
                .claim(CLAIM_USER_INFO_KEY, userInfo)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact()
    }

    private fun generateRefreshToken(): String {
        val now = Date()
        val expiration = Date(now.time + REFRESH_TOKEN_EXPIRATION)

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact()
    }

    companion object {
        private const val CLAIM_USER_INFO_KEY = "user-info"
        private const val ACCESS_TOKEN_EXPIRATION = 12 * 60 * 60 * 1000 // 12 hours
        private const val REFRESH_TOKEN_EXPIRATION = 30L * 24 * 60 * 60 * 1000 // 30 days
    }
}
