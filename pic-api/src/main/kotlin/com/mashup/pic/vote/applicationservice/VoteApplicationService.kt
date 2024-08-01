package com.mashup.pic.vote.applicationservice

import com.mashup.pic.domain.vote.VoteService
import com.mashup.pic.vote.controller.dto.VoteOptionItem
import com.mashup.pic.vote.controller.dto.VoteOptionResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable

@Service
@Transactional(readOnly = true)
class VoteApplicationService(
    private val voteService: VoteService
) {
    fun getVoteOptions(
        @PathVariable eventId: Long
    ): VoteOptionResponse {
        val options = voteService.getVoteOptions(eventId).map { VoteOptionItem(it.id, it.imageUrl) }
        return VoteOptionResponse(options)
    }
}
