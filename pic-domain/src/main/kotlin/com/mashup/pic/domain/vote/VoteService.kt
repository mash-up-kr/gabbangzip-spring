package com.mashup.pic.domain.vote

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.EventImageOptionDto
import com.mashup.pic.domain.event.EventImageOptionRepository
import com.mashup.pic.domain.event.EventJoin
import com.mashup.pic.domain.event.EventJoinRepository
import com.mashup.pic.domain.event.EventRepository
import com.mashup.pic.domain.event.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class VoteService(
    private val voteRepository: VoteRepository,
    private val eventRepository: EventRepository,
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

    fun getVoteOptions(eventId: Long): List<EventImageOptionDto> {
        validateEvent(eventId)
        val eventJoinIds = eventJoinRepository.findAllByEventId(eventId).map { it.id }
        return eventImageOptionRepository.findAllByEventJoinIdIn(eventJoinIds).map { it.toDto() }
    }

    private fun validateEvent(eventId: Long) {
        if (!eventRepository.existsById(eventId)) {
            throw PicException.of(PicExceptionType.NOT_EXIST, "없는 이벤트")
        }
    }

    private fun getEventJoinByUserIdAndEventId(
        userId: Long,
        eventId: Long
    ): EventJoin {
        return eventJoinRepository.findByUserIdAndEventId(userId, eventId) ?: throw PicException.of(PicExceptionType.NOT_EXIST)
    }
}
