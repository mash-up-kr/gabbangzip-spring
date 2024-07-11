package com.mashup.pic.domain.event

import java.time.LocalDateTime

class EventDto(
    val id: Long,
    val name: String,
    val description: String,
    val date: LocalDateTime
)

fun Event.toEventDto(): EventDto {
    return EventDto(
        id = this.id,
        name = this.name,
        description = this.description,
        date = this.date
    )
}
