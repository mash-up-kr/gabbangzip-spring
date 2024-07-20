package com.mashup.pic.group.applicationservice.dto

import com.mashup.pic.domain.group.GroupDto
import com.mashup.pic.domain.group.GroupKeyword

data class CreateGroupResponse(
    val id: Long,
    val groupName: String,
    val keyword: GroupKeyword,
    val groupImageUrl: String
) {
    companion object {
        fun from(groupDto: GroupDto): CreateGroupResponse {
            return CreateGroupResponse(
                id = groupDto.id,
                groupName = groupDto.name,
                keyword = groupDto.keyword,
                groupImageUrl = groupDto.imageUrl
            )
        }
    }
}
