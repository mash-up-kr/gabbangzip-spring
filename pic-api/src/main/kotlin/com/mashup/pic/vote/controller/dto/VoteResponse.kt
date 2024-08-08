package com.mashup.pic.vote.controller.dto

import com.mashup.pic.domain.group.GroupKeyword

data class VoteResponse(
    val eventId: Long,
    val randomImageUrl: String,
    val groupKeyword: GroupKeyword
)
