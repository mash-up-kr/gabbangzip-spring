package com.mashup.pic.event.applicationService

import com.mashup.pic.domain.event.EventService
import com.mashup.pic.domain.group.GroupService
import com.mashup.pic.event.applicationService.dto.CreateEventServiceRequest
import com.mashup.pic.event.controller.dto.CreateEventResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventApplicationService(
    private val eventService: EventService
) {
    @Transactional
    fun create(request: CreateEventServiceRequest): CreateEventResponse {
        return CreateEventResponse(eventService.create(
            groupId = request.groupId,
            description = request.description,
            date = request.date,
            pictures = request.pictures
        ))
    }
}
