package com.mashup.pic.domain.redis

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.event.EventService
import com.mashup.pic.domain.event.EventStatus
import com.mashup.pic.domain.result.ResultService
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

/**
 * key: {EventStatus}:{eventId}
 */
@Component
class RedisMessageListener(
    private val eventService: EventService,
    private val resultService: ResultService
) : MessageListener {
    override fun onMessage(
        message: Message,
        pattern: ByteArray?
    ) {
        val eventInfo = ExpiredEventInfo.parseMessage(message)
        when (ChannelTopic.from(eventInfo.topic)) {
            ChannelTopic.EVENT_OPEN -> eventService.endEventUploading(eventInfo.eventId)
            ChannelTopic.VOTE_OPEN -> {
                resultService.generateResult(eventInfo.eventId)
                eventService.endEventVoting(eventInfo.eventId)
            }
        }
    }
}

data class ExpiredEventInfo(
    val topic: String,
    val eventId: Long
) {
    companion object {
        fun parseMessage(message: Message): ExpiredEventInfo {
            val messageInfo = String(message.body).split(DELIMITER)
            return ExpiredEventInfo(messageInfo[0], messageInfo[1].toLong())
        }

        private const val DELIMITER = ":"
    }
}

enum class ChannelTopic(val description: String) {
    EVENT_OPEN("이벤트 개설"),
    VOTE_OPEN("투표 개설");

    companion object {
        fun from(topic: String): ChannelTopic {
            return ChannelTopic.entries.firstOrNull { it.name == topic }
                ?: throw PicException.of(PicExceptionType.SYSTEM_FAIL)
        }

        fun from(eventStatus: EventStatus): ChannelTopic {
            return when (eventStatus) {
                EventStatus.UPLOADING -> EVENT_OPEN
                EventStatus.VOTING -> VOTE_OPEN
                else -> throw PicException.of(PicExceptionType.SYSTEM_FAIL)
            }
        }
    }
}
