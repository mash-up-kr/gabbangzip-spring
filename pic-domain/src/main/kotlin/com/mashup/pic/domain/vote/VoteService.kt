package com.mashup.pic.domain.vote

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.EventImageOption
import com.mashup.pic.domain.event.EventImageOptionDto
import com.mashup.pic.domain.event.EventImageOptionRepository
import com.mashup.pic.domain.event.EventJoin
import com.mashup.pic.domain.event.EventJoinRepository
import com.mashup.pic.domain.event.EventRepository
import com.mashup.pic.domain.event.toDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

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

    @Transactional
    fun vote(
        userId: Long,
        eventId: Long,
        likedOptionIds: List<Long>
    ) {
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        val likedOptions = likedOptionIds.map { Vote(eventJoin.id, it) }
        voteRepository.saveAll(likedOptions)
    }

    @Transactional
    fun markVoted(
        userId: Long,
        eventId: Long
    ) {
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        eventJoin.voted = true
    }

    fun hasEveryoneVoted(eventId: Long): Boolean {
        val eventJoins = getAllEventJoinByEventId(eventId)
        return eventJoins.all { it.voted }
    }

    fun getVotedRandomImageUrl(
        userId: Long,
        eventId: Long
    ): String {
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        val voteOptionIds = getOptionByEventJoinId(eventJoin.id).map { it.eventImageOptionId }
        val options = getAllImageOptionByIds(voteOptionIds)

        if (options.isEmpty()) {
            return getRandomOptionByEventId(eventId).imageUrl
        }

        val randomIndex = Random.nextInt(options.size)
        return options[randomIndex].imageUrl
    }

    private fun getAllImageOptionByIds(eventOptionIds: List<Long>): List<EventImageOption> {
        return eventImageOptionRepository.findAllByIdIn(eventOptionIds)
    }

    private fun getRandomOptionByEventId(eventId: Long): EventImageOption {
        val eventJoinIds = eventJoinRepository.findAllByEventId(eventId).map { it.id }
        val eventOptions = eventImageOptionRepository.findAllByEventJoinIdIn(eventJoinIds)

        if (eventOptions.isEmpty()) {
            throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "옵션 없는 이벤트 오류")
        }

        val randomIndex = Random.nextInt(eventOptions.size)
        return eventOptions[randomIndex]
    }

    private fun getOptionByEventJoinId(eventJoinId: Long): List<Vote> {
        return voteRepository.findAllByEventJoinId(eventJoinId)
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

    private fun getAllEventJoinByEventId(eventId: Long): List<EventJoin> {
        return eventJoinRepository.findAllByEventId(eventId)
    }
}
