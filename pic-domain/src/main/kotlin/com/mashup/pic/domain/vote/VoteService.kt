package com.mashup.pic.domain.vote

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.EventImageOptionRepository
import com.mashup.pic.domain.event.EventJoin
import com.mashup.pic.domain.event.EventJoinRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class VoteService(
    private val voteRepository: VoteRepository,
    private val eventJoinRepository: EventJoinRepository,
    private val eventImageOptionRepository: EventImageOptionRepository
) {
    fun hasUserVoted(
        userId: Long,
        eventId: Long
    ): Boolean {
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        return voteRepository.existsByEventJoinId(eventJoin.id)
    }

    private fun getEventJoinByUserIdAndEventId(
        userId: Long,
        eventId: Long
    ): EventJoin {
        return eventJoinRepository.findByUserIdAndEventId(userId, eventId) ?: throw PicException.of(PicExceptionType.NOT_EXIST)
    }
}
