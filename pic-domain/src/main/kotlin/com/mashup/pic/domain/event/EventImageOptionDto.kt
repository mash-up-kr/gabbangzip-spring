package com.mashup.pic.domain.event

data class EventImageOptionDto(
    val id: Long,
    val eventJoinId: Long,
    val imageUrl: String
)

fun EventImageOption.toDto(): EventImageOptionDto {
    return EventImageOptionDto(
        id = this.id,
        eventJoinId = this.eventJoinId,
        imageUrl = this.imageUrl
    )
}
