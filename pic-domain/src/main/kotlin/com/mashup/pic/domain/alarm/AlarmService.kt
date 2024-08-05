package com.mashup.pic.domain.alarm

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.Event
import com.mashup.pic.domain.event.EventJoin
import com.mashup.pic.domain.event.EventJoinRepository
import com.mashup.pic.domain.event.EventRepository
import com.mashup.pic.domain.event.EventStatus
import com.mashup.pic.domain.group.Group
import com.mashup.pic.domain.group.GroupRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AlarmService(
    private val alarmTokenRepository: AlarmTokenRepository,
    private val eventJoinRepository: EventJoinRepository,
    private val eventRepository: EventRepository,
    private val groupRepository: GroupRepository,
    private val alarmRepository: AlarmRepository
) {
    fun findTokensByUserIds(userIds: List<Long>): List<String> {
        val tokens = alarmTokenRepository.findAllByUserIdIn(userIds)
        return tokens.map { it.token }
    }

    @Transactional
    fun registerTokenForUser(
        userId: Long,
        token: String
    ): String {
        val alarmToken = alarmTokenRepository.findByUserId(userId)

        if (alarmToken != null) {
            alarmToken.token = token
            alarmTokenRepository.save(alarmToken)
        } else {
            val newAlarmToken = AlarmToken(userId = userId, token = token)
            alarmTokenRepository.save(newAlarmToken)
        }

        return token
    }

    fun getKookTargetUserIds(eventId: Long): List<Long> {
        val event = getEventById(eventId)
        val eventJoins = getAllEventJoinByEventId(event.id)

        return when {
            event.eventStatus == EventStatus.UPLOADING -> {
                eventJoins.filter { !it.uploaded }
                    .map { it.userId }
            }
            else -> {
                eventJoins.filter { !it.voted }
                    .map { it.userId }
            }
        }
    }

    fun generateKookMessage(eventId: Long): AlarmMessageDto {
        val event = getEventById(eventId)
        val group = getGroupById(event.groupId)

        val alarmType =
            when (event.eventStatus) {
                EventStatus.UPLOADING -> AlarmType.UPLOAD_KOOK
                else -> AlarmType.VOTE_KOOK
            }

        val message = AlarmMessageGenerator.generateMessage(alarmType, groupName = group.name)

        return AlarmMessageDto(alarmType.title, message)
    }

    @Transactional
    fun saveAlarm(
        userId: Long,
        title: String,
        body: String
    ) {
        alarmRepository.save(Alarm(userId, title, body))
    }

    private fun getGroupById(groupId: Long): Group {
        return groupRepository.findByIdOrNull(groupId) ?: throw PicException.of(PicExceptionType.NOT_EXIST, "$groupId 는 없는 그룹")
    }

    private fun getEventById(eventId: Long): Event {
        return eventRepository.findByIdOrNull(eventId) ?: throw PicException.of(PicExceptionType.NOT_EXIST, "$eventId 는 없는 이벤트")
    }

    private fun getAllEventJoinByEventId(eventId: Long): List<EventJoin> {
        return eventJoinRepository.findAllByEventId(eventId)
    }
}
