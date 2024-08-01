package com.mashup.pic.vote.controller.dto

data class VoteOptionResponse(
    val options: List<VoteOptionItem>
)

data class VoteOptionItem(
    val optionId: Long,
    val imageUrl: String
)
