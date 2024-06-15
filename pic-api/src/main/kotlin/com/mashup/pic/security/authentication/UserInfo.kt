package com.mashup.pic.security.authentication

import com.mashup.pic.domain.user.User
import com.mashup.pic.domain.user.UserRole


data class UserInfo(
        val id: Long,
        val nickname: String,
        val roles: Set<UserRole>
)
