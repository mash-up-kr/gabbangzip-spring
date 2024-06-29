package com.mashup.pic.external.common.response

import com.fasterxml.jackson.annotation.JsonProperty

data class JwksResponse(
    @JsonProperty("keys")
    val keys: List<JwkKey>
) {
    fun getJwkKeyByKid(kid: String): JwkKey? {
        return keys.find { it.kid == kid }
    }
}

data class JwkKey(
    @JsonProperty("kid")
    val kid: String,
    @JsonProperty("kty")
    val kty: String,
    @JsonProperty("alg")
    val alg: String,
    @JsonProperty("use")
    val use: String,
    @JsonProperty("n")
    val n: String,
    @JsonProperty("e")
    val e: String
)
