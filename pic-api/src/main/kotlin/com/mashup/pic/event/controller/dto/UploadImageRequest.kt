package com.mashup.pic.event.controller.dto

import com.mashup.pic.event.applicationService.dto.UploadImageServiceRequest

data class UploadImageRequest(
    val eventId: Long,
    val imageUrls: List<String>
)

fun UploadImageRequest.toServiceRequest(userId: Long): UploadImageServiceRequest = UploadImageServiceRequest(userId, eventId, imageUrls)
