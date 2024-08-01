package com.mashup.pic.domain.event

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EventImageOptionRepository : JpaRepository<EventImageOption, Long> {
    fun existsByEventJoinId(id: Long): Boolean

    fun findAllByEventJoinIdIn(eventJoinIds: List<Long>): List<EventImageOption>

    @Query("SELECT COUNT(DISTINCT e.eventJoinId) FROM EventImageOption e")
    fun countDistinctEventJoinIds(): Int
}
