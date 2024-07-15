package com.mashup.pic.group.applicationservice.dto

data class CreateGroupServiceRequest(
    val userId: Long,
    val groupName: String,
    val keywordId: Long,
    val groupImageUrl: String
)
