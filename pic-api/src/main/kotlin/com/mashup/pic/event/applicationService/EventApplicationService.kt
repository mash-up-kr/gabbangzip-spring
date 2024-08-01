package com.mashup.pic.event.applicationService

import com.mashup.pic.domain.event.EventService
import com.mashup.pic.event.applicationService.dto.CreateEventServiceRequest
import com.mashup.pic.event.applicationService.dto.UploadImageServiceRequest
import com.mashup.pic.event.controller.dto.CreateEventResponse
import com.mashup.pic.event.controller.dto.UploadImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventApplicationService(
    private val eventService: EventService
) {
    @Transactional
    fun create(request: CreateEventServiceRequest): CreateEventResponse {
        return CreateEventResponse(
            eventService.create(
                userId = request.userId,
                groupId = request.groupId,
                description = request.description,
                date = request.date,
                pictures = request.pictures
            )
        )
    }

    @Transactional
    fun uploadImages(request: UploadImageServiceRequest): UploadImageResponse {
        eventService.addImageOptions(
            userId = request.userId,
            eventId = request.eventId,
            imageUrls = request.imageUrls
        )

        if (eventService.hasEveryoneUploadedImages(request.eventId)) {
            eventService.endEventUploading(request.eventId)
        }

        return UploadImageResponse(request.eventId)
    }
}
