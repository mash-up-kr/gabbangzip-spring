package com.mashup.pic.domain.event

import org.springframework.data.jpa.repository.JpaRepository

interface EventImageOptionRepository : JpaRepository<EventImageOption, Long> {
    fun existsByEventJoinId(id: Long): Boolean

    fun findAllByEventJoinIdIn(eventJoinIds: List<Long>): List<EventImageOption>

    fun findAllByIdIn(ids: List<Long>): List<EventImageOption>
}
