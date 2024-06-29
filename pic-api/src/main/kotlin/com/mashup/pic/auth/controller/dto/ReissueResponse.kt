package com.mashup.pic.auth.controller.dto

import com.mashup.pic.security.authentication.AuthToken

data class ReissueResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(authToken: AuthToken): ReissueResponse {
            return ReissueResponse(
                accessToken = authToken.accessToken,
                refreshToken = authToken.refreshToken
            )
        }
    }
}
