package com.mashup.pic.security.oidc

interface IdTokenValidator {
    fun validateAndGetId(idToken: String, nickname: String): Long
}
