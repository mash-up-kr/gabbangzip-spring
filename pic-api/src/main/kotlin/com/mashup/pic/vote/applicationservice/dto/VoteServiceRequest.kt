package com.mashup.pic.vote.applicationservice.dto

data class VoteServiceRequest(
    val userId: Long,
    val eventId: Long,
    val likedOptionIds: List<Long>
)
