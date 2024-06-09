package com.mashup.pic.security.authentication

import com.mashup.pic.domain.user.User
import com.mashup.pic.domain.user.UserRole


data class UserInfo(
        val id: Long,
        val nickname: String,
        val roles: Set<UserRole>
) {
    companion object {
        fun from(user: User): UserInfo {
            return UserInfo(
                    user.id,
                    user.nickname,
                    user.roles
            )
        }
    }
}
