package com.mashup.pic.domain.event

import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, Long>
