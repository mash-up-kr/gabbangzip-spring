package com.mashup.pic.domain.event

import org.springframework.data.jpa.repository.JpaRepository

interface EventJoinRepository : JpaRepository<EventJoin, Long> {
    fun findByUserIdAndEventId(
        userId: Long,
        eventId: Long
    ): EventJoin?

    fun findAllByEventId(eventId: Long): List<EventJoin>
}
