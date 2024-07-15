package com.mashup.pic.group.applicationservice.dto

import com.mashup.pic.domain.group.GroupDto
import com.mashup.pic.domain.group.KeywordDto

data class CreateGroupResponse(
    val id: Long,
    val groupName: String,
    val keyword: KeywordResponse,
    val groupImageUrl: String
) {
    companion object {
        fun from(groupDto: GroupDto): CreateGroupResponse {
            return CreateGroupResponse(
                id = groupDto.id,
                groupName = groupDto.name,
                keyword = KeywordResponse.from(groupDto.keywordDto),
                groupImageUrl = groupDto.imageUrl
            )
        }
    }
}

data class KeywordResponse(
    val id: Long,
    val name: String
) {
    companion object {
        fun from(keywordDto: KeywordDto): KeywordResponse {
            return KeywordResponse(keywordDto.id, keywordDto.name)
        }

        fun sample(): KeywordResponse {
            return KeywordResponse(1L, "Sample Keyword")
        }
    }
}
