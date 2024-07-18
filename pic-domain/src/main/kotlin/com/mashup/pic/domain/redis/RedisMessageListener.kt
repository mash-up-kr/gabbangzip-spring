package com.mashup.pic.domain.redis

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

/**
 * TODO
 * key는 EVENT:{status}:{eventId}???
 * 2시간마다 키 만료될때 만료 이벤트 발행해서 이벤트 도메인 객체 상태 업데이트
 * applicationEventPublisher로 이벤트 발행해서 의존성 분리? 혹은 여기서 로직 진행?
 */
@Component
class RedisMessageListener(
    private val publisher: ApplicationEventPublisher
) : MessageListener {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val eventInfo = ExpiredEventInfo.parseMessage(message)
        when (ChannelTopic.from(eventInfo.topic)) {
            ChannelTopic.EVENT_OPEN -> TODO("VOTE_OPEN으로 업데이트")
            ChannelTopic.VOTE_OPEN -> TODO("VOTE_FINISHED로 업데이트")
        }
    }
}

data class ExpiredEventInfo(
    val topic: String,
    val eventId: String
) {
    companion object {
        fun parseMessage(message: Message): ExpiredEventInfo {
            val messageInfo = String(message.body).split(delimiter)
            return ExpiredEventInfo(messageInfo[0], messageInfo[1])
        }

        private const val delimiter = ":"
    }
}

/**
 * 이벤트 객체 status enum으로 대체하기
 * 테스트용 임시 객체
 */
enum class ChannelTopic(val description: String, val nextStep: String) {
    EVENT_OPEN("이벤트 개설", "VOTE_OPEN"),
    VOTE_OPEN("투표 개설", "VOTE_FINISHED");

    companion object {
        fun from(topic: String): ChannelTopic {
            return ChannelTopic.entries.firstOrNull { it.name == topic }
                ?: throw PicException.of(PicExceptionType.SYSTEM_FAIL)
        }
    }
}
