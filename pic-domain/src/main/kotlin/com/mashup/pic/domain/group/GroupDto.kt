package com.mashup.pic.domain.group

import java.time.LocalDateTime

data class GroupDto(
    val id: Long,
    val name: String,
    val keyword: GroupKeyword,
    val imageUrl: String,
    val createdAt: LocalDateTime
)

fun Group.toDto(): GroupDto = GroupDto(id, name, keyword, imageUrl, createdAt)

data class GroupJoinDto(
    val id: Long,
    val userId: Long,
    val groupId: Long
)

fun GroupJoin.toDto(): GroupJoinDto = GroupJoinDto(id, userId, groupId)
