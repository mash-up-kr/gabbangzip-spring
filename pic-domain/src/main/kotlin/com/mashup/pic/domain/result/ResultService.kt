package com.mashup.pic.domain.result

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.EventImageOption
import com.mashup.pic.domain.event.EventImageOptionRepository
import com.mashup.pic.domain.event.EventJoin
import com.mashup.pic.domain.event.EventJoinRepository
import com.mashup.pic.domain.vote.Vote
import com.mashup.pic.domain.vote.VoteRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class ResultService(
    private val eventJoinRepository: EventJoinRepository,
    private val eventImageOptionRepository: EventImageOptionRepository,
    private val voteRepository: VoteRepository,
    private val resultRepository: ResultRepository
) {
    @Transactional
    fun generateResult(eventId: Long) {
        val eventJoinIds = getEvenJoinsByEventId(eventId).map { it.id }
        val imageOptionIds = getImageOptionIdsByEventJoins(eventJoinIds).map { it.id }
        val votes = getVotesByImageOptionIds(imageOptionIds)
        val frequencyMap = votes.groupingBy { it.eventImageOptionId }.eachCount()
        val voteResults =
            imageOptionIds.map { imageOptionId ->
                Pair(imageOptionId, frequencyMap[imageOptionId] ?: 0)
            }
        val resultOptions =
            voteResults.sortedWith(compareByDescending<Pair<Long, Int>> { it.second }.thenBy { it.first })
                .take(4)

        val resultsToSave =
            resultOptions.mapIndexed { index, (imageOptionId) ->
                Result(
                    eventId = eventId,
                    eventImageOptionId = imageOptionId,
                    frame = getRandomFrame(),
                    imageOrder = index + 1
                )
            }
        resultRepository.saveAll(resultsToSave)
    }

    fun getResultOfEvent(eventId: Long): ResultDto {
        return ResultDto(
            getResultsByEventId(eventId).map { result ->
                val imageUrl = getImageOptionById(result.eventImageOptionId).imageUrl
                ResultItem(
                    imageUrl = imageUrl,
                    frame = result.frame
                )
            }
        )
    }

    fun hasResult(eventId: Long): Boolean {
        return resultRepository.findAllByEventId(eventId).isNotEmpty()
    }

    private fun getImageOptionById(imageOptionId: Long): EventImageOption {
        return eventImageOptionRepository.findByIdOrNull(imageOptionId)
            ?: throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "없는 이미지 항목")
    }

    private fun getResultsByEventId(eventId: Long): List<Result> {
        return resultRepository.findAllByEventIdOrderByImageOrderAsc(eventId)
    }

    private fun getVotesByImageOptionIds(imageOptionIds: List<Long>): List<Vote> {
        return voteRepository.findAllByEventImageOptionIdIn(imageOptionIds)
    }

    private fun getEvenJoinsByEventId(eventId: Long): List<EventJoin> {
        return eventJoinRepository.findAllByEventId(eventId)
    }

    private fun getImageOptionIdsByEventJoins(eventJoinIds: List<Long>): List<EventImageOption> {
        return eventImageOptionRepository.findAllByEventJoinIdIn(eventJoinIds)
    }

    fun getRandomFrame(): Frame {
        val frames = Frame.entries.toTypedArray()
        return frames[Random.nextInt(frames.size)]
    }
}
