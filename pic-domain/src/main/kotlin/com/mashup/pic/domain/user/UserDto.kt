package com.mashup.pic.domain.user

data class UserDto(
    val id: Long,
    val oAuthId: Long,
    val nickname: String,
    val profileImage: String,
    val roles: Set<UserRole>,
)
