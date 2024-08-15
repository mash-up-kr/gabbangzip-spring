package com.mashup.pic.domain.event

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.group.GroupJoinRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class EventService(
    private val eventRepository: EventRepository,
    private val groupJoinRepository: GroupJoinRepository,
    private val eventJoinRepository: EventJoinRepository,
    private val eventImageOptionRepository: EventImageOptionRepository,
    private val eventRedisRepository: EventRedisRepository
) {
    @Transactional
    fun create(
        userId: Long,
        groupId: Long,
        description: String,
        date: LocalDateTime,
        pictures: List<String>
    ): Long {
        val event =
            eventRepository.save(
                Event(
                    groupId = groupId,
                    description = description,
                    date = date
                )
            )

        createEventJoinsByGroup(event.id, groupId)
        val creatorEventJoin = getEventJoinByUserIdAndEventId(userId, event.id)

        val eventImageOptions =
            pictures.map { picture ->
                EventImageOption(creatorEventJoin.id, picture)
            }
        eventImageOptionRepository.saveAll(eventImageOptions)
        eventRedisRepository.setEventStatusExpiredTime(currentEventStatus = EventStatus.UPLOADING, eventId = event.id)

        return event.id
    }

    fun getLastEvent(groupId: Long): EventDto? {
        return eventRepository.findTopByGroupIdOrderByIdDesc(groupId)?.toDto()
    }

    fun getRandomImageOptionFromEvent(eventId: Long): String {
        val eventJoinIds = eventJoinRepository.findAllByEventId(eventId).map { it.id }
        val imageOptions = eventImageOptionRepository.findAllByEventJoinIdIn(eventJoinIds)

        return imageOptions[Random.nextInt(imageOptions.size)].imageUrl
    }

    @Transactional
    fun endEventUploading(eventId: Long) {
        val event = getEventById(eventId)
        if (event.eventStatus == EventStatus.UPLOADING) {
            event.eventStatus = EventStatus.VOTING
            event.uploadingEndDate = LocalDateTime.now()
            eventRedisRepository.setEventStatusExpiredTime(currentEventStatus = EventStatus.VOTING, eventId = eventId)
        }
    }

    @Transactional
    fun endEventVoting(eventId: Long) {
        val event = getEventById(eventId)
        if (event.eventStatus == EventStatus.VOTING) {
            event.eventStatus = EventStatus.COMPLETE
            event.votingEndDate = LocalDateTime.now()
        }
    }

    fun hasVisitedEvent(
        userId: Long,
        eventId: Long
    ): Boolean {
        return getEventJoinByUserIdAndEventId(userId, eventId).isVisited
    }

    fun getAllEventsAsc(groupId: Long): List<EventDto> {
        val events = eventRepository.findAllByGroupIdOrderByIdDesc(groupId)
        return events.map { it.toDto() }
    }

    @Transactional
    fun markVisited(
        userId: Long,
        eventId: Long
    ) {
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        eventJoin.isVisited = true
    }

    @Transactional
    fun joinEvent(
        userId: Long,
        eventId: Long
    ) {
        eventJoinRepository.save(
            EventJoin(
                userId = userId,
                eventId = eventId
            )
        )
    }

    private fun createEventJoinsByGroup(
        eventId: Long,
        groupId: Long
    ) {
        val eventJoins =
            groupJoinRepository.findAllByGroupId(groupId).map { groupJoin ->
                EventJoin(
                    userId = groupJoin.userId,
                    eventId = eventId
                )
            }

        eventJoinRepository.saveAll(eventJoins)
    }

    private fun getEventById(eventId: Long): Event {
        return eventRepository.findByIdOrNull(eventId) ?: throw PicException.of(PicExceptionType.NOT_EXIST, "$eventId 는 없는 이벤트")
    }

    private fun getEventJoinByUserIdAndEventId(
        userId: Long,
        eventId: Long
    ): EventJoin {
        return eventJoinRepository.findByUserIdAndEventId(userId, eventId) ?: throw PicException.of(PicExceptionType.NOT_EXIST)
    }
}
