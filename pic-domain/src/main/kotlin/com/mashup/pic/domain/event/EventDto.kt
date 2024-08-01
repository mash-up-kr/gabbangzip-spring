package com.mashup.pic.domain.event

import java.time.LocalDateTime

data class EventDto(
    val id: Long,
    val groupId: Long,
    val description: String,
    val date: LocalDateTime,
    val eventStatus: EventStatus,
    val uploadingEndDate: LocalDateTime?,
    val votingEndDate: LocalDateTime?
)

fun Event.toDto(): EventDto {
    return EventDto(
        id = this.id,
        groupId = this.groupId,
        description = this.description,
        date = this.date,
        eventStatus = this.eventStatus,
        uploadingEndDate = this.uploadingEndDate,
        votingEndDate = this.votingEndDate
    )
}
