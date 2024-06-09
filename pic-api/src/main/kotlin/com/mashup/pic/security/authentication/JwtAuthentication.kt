package com.mashup.pic.security.authentication

import com.mashup.pic.domain.user.UserRole
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class JwtAuthentication(private val userInfo: UserInfo) : Authentication {

    private var authenticated: Boolean = false

    override fun getName(): String {
        return userInfo.nickname
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return userInfo.roles.map(this::convertUserRoleToGrantedAuthority).toMutableList()
    }

    override fun getCredentials(): Any {
        return userInfo
    }

    override fun getDetails(): Any {
        return userInfo
    }

    override fun getPrincipal(): Any {
        return userInfo
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    private fun convertUserRoleToGrantedAuthority(userRole: UserRole): GrantedAuthority {
        return GrantedAuthority { userRole.role }
    }
}
