package com.mashup.pic.domain.result

import org.springframework.data.jpa.repository.JpaRepository

interface ResultRepository : JpaRepository<Result, Long> {
    fun findAllByEventIdOrderByImageOrderAsc(eventId: Long): List<Result>
}
