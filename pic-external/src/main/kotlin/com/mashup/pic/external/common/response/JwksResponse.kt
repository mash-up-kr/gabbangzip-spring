package com.mashup.pic.external.common.response

data class JwksResponse(
        val keys: List<JwkKey>
) {
    fun getJwkKeyByKid(kid: String): JwkKey? {
        return keys.find { it.kid == kid }
    }
}

data class JwkKey(
        val kid: String,
        val kty: String,
        val alg: String,
        val use: String,
        val n: String,
        val e: String
)
