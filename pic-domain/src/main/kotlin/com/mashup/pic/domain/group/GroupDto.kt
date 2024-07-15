package com.mashup.pic.domain.group

data class GroupDto(
    val id: Long,
    val name: String,
    val keywordDto: KeywordDto,
    val imageUrl: String
)

fun Group.toDto(): GroupDto = GroupDto(id, name, keyword.toDto(), imageUrl)

data class GroupJoinDto(
    val id: Long,
    val userId: Long,
    val groupId: Long
)

fun GroupJoin.toDto(): GroupJoinDto = GroupJoinDto(id, user.id, group.id)
