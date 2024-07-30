package com.mashup.pic.domain.event

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.group.GroupJoinRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class EventService(
    private val eventRepository: EventRepository,
    private val groupJoinRepository: GroupJoinRepository,
    private val eventJoinRepository: EventJoinRepository,
    private val eventImageOptionRepository: EventImageOptionRepository
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
        // TODO: 이벤트 생성하면서 생성한 사람에 대하여 이미지 후보군 바로 등록하기

        return event.id
    }

    fun getLastEvent(groupId: Long): EventDto? {
        return eventRepository.findTopByGroupIdOrderByDateDesc(groupId)?.toDto()
    }

    fun getRandomImageOption(id: Long): String {
        // TODO: 등록된 후보 중 랜덤으로 1개 URL 반환
        return "TODOTODO"
    }

    fun endEventUploading(eventId: Long) {
        val event = getEventById(eventId)

        event.eventStatus = EventStatus.COMPLETE
        event.uploadingEndDate = LocalDateTime.now()
        eventRepository.save(event)
    }

    fun endEventVoting(eventId: Long) {
        val event = getEventById(eventId)

        event.eventStatus = EventStatus.VOTING
        event.votingEndDate = LocalDateTime.now()
        eventRepository.save(event)
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
}
