package com.mashup.pic.domain.user

data class UserDto(
    val id: Long,
    val oAuthId: Long,
    val nickname: String,
    val profileImage: String,
    val roles: Set<UserRole>,
)

fun User.toUserDto(): UserDto {
    return UserDto(
        id = this.id,
        oAuthId = this.oAuthId,
        nickname = this.nickname,
        profileImage = this.profileImage,
        roles = this.roles,
    )
}
