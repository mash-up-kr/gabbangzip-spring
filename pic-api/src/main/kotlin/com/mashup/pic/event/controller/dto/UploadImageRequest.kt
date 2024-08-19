package com.mashup.pic.event.controller.dto

import com.mashup.pic.event.applicationService.dto.UploadImageServiceRequest
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UploadImageRequest(
    @field:NotNull val eventId: Long,
    @field:Size(min = 1) val imageUrls: List<String>
)

fun UploadImageRequest.toServiceRequest(userId: Long): UploadImageServiceRequest = UploadImageServiceRequest(userId, eventId, imageUrls)
