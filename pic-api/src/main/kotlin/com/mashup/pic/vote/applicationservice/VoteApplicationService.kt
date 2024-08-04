package com.mashup.pic.vote.applicationservice

import com.mashup.pic.domain.event.EventService
import com.mashup.pic.domain.result.ResultService
import com.mashup.pic.domain.vote.VoteService
import com.mashup.pic.vote.applicationservice.dto.VoteServiceRequest
import com.mashup.pic.vote.controller.dto.VoteOptionItem
import com.mashup.pic.vote.controller.dto.VoteOptionResponse
import com.mashup.pic.vote.controller.dto.VoteResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class VoteApplicationService(
    private val voteService: VoteService,
    private val eventService: EventService,
    private val resultService: ResultService
) {
    fun getVoteOptions(eventId: Long): VoteOptionResponse {
        val options = voteService.getVoteOptions(eventId).map { VoteOptionItem(it.id, it.imageUrl) }
        return VoteOptionResponse(options)
    }

    @Transactional
    fun vote(request: VoteServiceRequest): VoteResponse {
        voteService.vote(
            userId = request.userId,
            eventId = request.eventId,
            likedOptionIds = request.likedOptionIds
        )
        voteService.markVoted(request.userId, request.eventId)

        if (voteService.hasEveryoneVoted(request.eventId)) {
            eventService.endEventVoting(request.eventId)
            resultService.generateResult(10)
        }
        return VoteResponse(request.eventId)
    }
}
