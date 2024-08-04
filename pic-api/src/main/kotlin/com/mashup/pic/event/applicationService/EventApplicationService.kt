package com.mashup.pic.event.applicationService

import com.mashup.pic.domain.event.EventService
import com.mashup.pic.domain.event.UploadService
import com.mashup.pic.event.applicationService.dto.CreateEventServiceRequest
import com.mashup.pic.event.applicationService.dto.MarkEventVisitedServiceRequest
import com.mashup.pic.event.applicationService.dto.UploadImageServiceRequest
import com.mashup.pic.event.controller.dto.CreateEventResponse
import com.mashup.pic.event.controller.dto.MarkEventVisitedResponse
import com.mashup.pic.event.controller.dto.UploadImageResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EventApplicationService(
    private val eventService: EventService,
    private val uploadService: UploadService
) {
    @Transactional
    fun create(request: CreateEventServiceRequest): CreateEventResponse {
        val savedEvent =
            CreateEventResponse(
                eventService.create(
                    userId = request.userId,
                    groupId = request.groupId,
                    description = request.description,
                    date = request.date,
                    pictures = request.pictures
                )
            )
        uploadService.markUploaded(request.userId, savedEvent.id)
        return savedEvent
    }

    @Transactional
    fun uploadImages(request: UploadImageServiceRequest): UploadImageResponse {
        uploadService.addImageOptions(
            userId = request.userId,
            eventId = request.eventId,
            imageUrls = request.imageUrls
        )
        uploadService.markUploaded(request.userId, request.eventId)

        if (uploadService.hasEveryoneUploadedImages(request.eventId)) {
            eventService.endEventUploading(request.eventId)
        }

        return UploadImageResponse(request.eventId)
    }

    @Transactional
    fun markEventVisit(request: MarkEventVisitedServiceRequest): MarkEventVisitedResponse {
        eventService.markVisited(request.userId, request.eventId)
        return MarkEventVisitedResponse(true)
    }
}
