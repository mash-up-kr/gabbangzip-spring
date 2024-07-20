package com.mashup.pic.group.applicationservice.dto

import com.mashup.pic.domain.group.GroupKeyword

data class CreateGroupServiceRequest(
    val userId: Long,
    val groupName: String,
    val keyword: GroupKeyword,
    val groupImageUrl: String
)
