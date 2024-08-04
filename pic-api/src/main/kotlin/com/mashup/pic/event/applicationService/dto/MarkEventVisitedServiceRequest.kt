package com.mashup.pic.event.applicationService.dto

import com.mashup.pic.event.controller.dto.MarkEventVisitedRequest

data class MarkEventVisitedServiceRequest(
    val userId: Long,
    val eventId: Long
)

fun MarkEventVisitedRequest.toServiceRequest(userId: Long): MarkEventVisitedServiceRequest {
    return MarkEventVisitedServiceRequest(
        userId = userId,
        eventId = this.eventId
    )
}
