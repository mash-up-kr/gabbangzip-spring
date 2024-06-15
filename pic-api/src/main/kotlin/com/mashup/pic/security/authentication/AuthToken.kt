package com.mashup.pic.security.authentication

data class AuthToken(
        val accessToken: String,
        val refreshToken: String
)
