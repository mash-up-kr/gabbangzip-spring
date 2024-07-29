package com.mashup.pic.domain.event

import com.mashup.pic.domain.group.GroupJoinRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class EventService(
    private val eventRepository: EventRepository,
    private val groupJoinRepository: GroupJoinRepository,
    private val eventJoinRepository: EventJoinRepository
) {
    @Transactional
    fun create(
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

        return event.id
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

    @Transactional
    fun deleteEvent(eventId: Long) {
        eventRepository.deleteById(eventId)
    }
}
