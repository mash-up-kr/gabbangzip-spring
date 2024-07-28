package com.mashup.pic.domain.event

import java.time.LocalDateTime

class EventDto(
    val id: Long,
    val groupId: Long,
    val description: String,
    val date: LocalDateTime
)

fun Event.toEventDto(): EventDto {
    return EventDto(
        id = this.id,
        groupId = this.group.id,
        description = this.description,
        date = this.date
    )
}
