package com.mashup.pic.security

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = CustomSecurityContextFactory::class)
annotation class WithCustomUser(
    val id: Long,
    val nickname: String
)
