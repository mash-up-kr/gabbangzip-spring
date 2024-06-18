package com.mashup.pic.domain.common

import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        val redisTemplate = RedisTemplate<Any, Any>()
        redisTemplate.setConnectionFactory(lettuceConnectionFactory())
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        return redisTemplate
    }

    @Bean
    fun cacheManager(): CacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))
            .entryTtl(Duration.ofMinutes(2))
            .disableCachingNullValues()
        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(lettuceConnectionFactory())
            .cacheDefaults(configuration)
            .build()
    }

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory()
    }
}
