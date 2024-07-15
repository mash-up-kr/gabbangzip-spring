package com.mashup.pic.fake

import com.mashup.pic.security.oidc.IdTokenValidator
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class FakeIdTokenValidator : IdTokenValidator {
    override fun validateAndGetId(
        idToken: String,
        nickname: String
    ): Long {
        return -1L
    }
}
