package com.mashup.pic.group.applicationservice.dto

import com.mashup.pic.domain.group.GroupDto
import com.mashup.pic.domain.group.GroupKeyword

data class CreateGroupResponse(
    val id: Long,
    val groupName: String,
    val keyword: GroupKeyword,
    val groupImageUrl: String,
    val invitationCode: String
) {
    companion object {
        fun from(
            groupDto: GroupDto,
            code: String
        ): CreateGroupResponse {
            return CreateGroupResponse(
                id = groupDto.id,
                groupName = groupDto.name,
                keyword = groupDto.keyword,
                groupImageUrl = groupDto.imageUrl,
                invitationCode = code
            )
        }
    }
}
