package com.mashup.pic.event.applicationService.dto

import com.mashup.pic.event.controller.dto.CreateEventRequest
import java.time.LocalDateTime

data class CreateEventServiceRequest(
    val groupId: Long,
    val description: String,
    val date: LocalDateTime,
    val pictures: List<String>
)

fun CreateEventRequest.toServiceRequest(): CreateEventServiceRequest {
    return CreateEventServiceRequest(
        groupId = this.groupId,
        description = this.description,
        date = this.date,
        pictures = this.pictures
    )
}
