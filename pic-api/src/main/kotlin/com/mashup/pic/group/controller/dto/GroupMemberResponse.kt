package com.mashup.pic.group.controller.dto

data class GroupMemberResponse(
    val members: List<Member>,
    val invitationCode: String
)

data class Member(
    val id: Long,
    val nickname: String
)
