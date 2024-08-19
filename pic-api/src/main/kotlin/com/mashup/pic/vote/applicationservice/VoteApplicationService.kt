package com.mashup.pic.vote.applicationservice

import com.mashup.pic.domain.event.EventService
import com.mashup.pic.domain.group.GroupService
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
    private val resultService: ResultService,
    private val groupService: GroupService
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
            resultService.generateResult(request.eventId)
            eventService.endEventVoting(request.eventId)
        }

        val randomImageUrl = voteService.getVotedRandomImageUrl(request.userId, request.eventId)
        val groupKeyword = groupService.getGroupByEventId(request.eventId)
        return VoteResponse(
            eventId = request.eventId,
            randomImageUrl = randomImageUrl,
            groupKeyword = groupKeyword
        )
    }
}
