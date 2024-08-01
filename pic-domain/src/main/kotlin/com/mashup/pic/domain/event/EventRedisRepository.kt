package com.mashup.pic.domain.event

import com.mashup.pic.domain.redis.ChannelTopic
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class EventRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {
    fun setEventStatusExpiredTime(currentEventStatus: EventStatus, eventId: Long) {
        redisTemplate.opsForValue().set("${ChannelTopic.from(currentEventStatus)}:$eventId", "$eventId", Duration.ofHours(EXPIRED_TIME_HOUR))
    }

    companion object {
        private const val EXPIRED_TIME_HOUR = 2L
    }
}
