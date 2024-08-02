package com.mashup.pic.vote.controller.dto

import com.mashup.pic.vote.applicationservice.dto.VoteServiceRequest
import jakarta.validation.constraints.Size

data class VoteRequest(
    val eventId: Long,
    @field:Size(min = 1) val likedOptionIds: List<Long>
)

fun VoteRequest.toServiceRequest(userId: Long): VoteServiceRequest = VoteServiceRequest(userId, this.eventId, this.likedOptionIds)
