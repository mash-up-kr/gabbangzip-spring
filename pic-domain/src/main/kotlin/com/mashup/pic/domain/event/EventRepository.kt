package com.mashup.pic.domain.event

import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, Long> {
    fun findTopByGroupIdOrderByIdDesc(groupId: Long): Event?

    fun findAllByGroupIdOrderByIdDesc(groupId: Long): List<Event>

    fun findAllByGroupId(groupId: Long): List<Event>
}
