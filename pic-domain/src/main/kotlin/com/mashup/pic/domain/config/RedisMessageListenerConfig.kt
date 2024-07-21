package com.mashup.pic.domain.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisKeyValueAdapter
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(
    enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP
)
class RedisMessageListenerConfig {
    @Bean
    fun messageListenerAdaptor(redisMessageListener: MessageListener): MessageListenerAdapter {
        return MessageListenerAdapter(redisMessageListener)
    }

    @Bean
    fun redisContainer(
        redisConnectionFactory: RedisConnectionFactory,
        messageListenerAdapter: MessageListenerAdapter
    ): RedisMessageListenerContainer {
        return RedisMessageListenerContainer().apply {
            setConnectionFactory(redisConnectionFactory)
            addMessageListener(messageListenerAdapter, ChannelTopic(EXPIRE_EVENT_TOPIC))
        }
    }

    companion object {
        private const val EXPIRE_EVENT_TOPIC = "__keyevent@0__:expired"
    }
}
