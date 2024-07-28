package com.mashup.pic.domain.event

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.group.Group
import com.mashup.pic.domain.group.GroupJoin
import com.mashup.pic.domain.group.GroupRepository
import com.mashup.pic.domain.group.GroupService
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties.ShowSummary
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class EventService(
    private val eventRepository: EventRepository,
    private val groupRepository: GroupRepository,
    private val eventJoinRepository: EventJoinRepository
) {
    @Transactional
    fun create(
        groupId: Long,
        description: String,
        date: LocalDateTime,
        pictures: List<String>
    ): Long {
        println("=============================" + groupId);
        val group = groupRepository.findByIdOrNull(groupId)
            ?: throw PicException.of(
                type = PicExceptionType.NOT_EXIST,
                message = "$groupId 에 해당하는 그룹를 찾을 수 없습니다."
            )

        val event = eventRepository.save(
            Event(
                group = group,
                description = description,
                date = date
            )
        )
        createEventJoinsByGroup(event, group)

        return event.id
    }

    private fun createEventJoinsByGroup(event: Event, group: Group) {
        val eventJoins = group.groupJoins.map { groupJoin ->
            EventJoin(
                user = groupJoin.user,
                event = event
            )
        }

        eventJoinRepository.saveAll(eventJoins)
    }

    @Transactional
    fun deleteEvent(eventId: Long) {
        eventRepository.deleteById(eventId)
    }

}
