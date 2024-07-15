package com.mashup.pic.security

import com.mashup.pic.domain.user.UserRole
import com.mashup.pic.security.authentication.JwtAuthentication
import com.mashup.pic.security.authentication.UserInfo
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class CustomSecurityContextFactory : WithSecurityContextFactory<WithCustomUser> {
    override fun createSecurityContext(withCustomUser: WithCustomUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        val authentication =
            JwtAuthentication(
                UserInfo(withCustomUser.id, withCustomUser.nickname, setOf(UserRole.MEMBER))
            ).also { it.isAuthenticated = true }
        context.authentication = authentication
        return context
    }
}
