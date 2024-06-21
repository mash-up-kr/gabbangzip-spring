package com.mashup.pic.domain.auth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refresh_token", timeToLive = 30L * 24 * 60 * 60 * 1000)
class RefreshToken (
    @Id
    var refreshToken: String,
    var userId: Long
)
