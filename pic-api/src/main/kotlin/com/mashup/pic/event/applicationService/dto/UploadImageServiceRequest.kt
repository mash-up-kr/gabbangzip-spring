package com.mashup.pic.event.applicationService.dto

data class UploadImageServiceRequest(
    val userId: Long,
    val eventId: Long,
    val imageUrls: List<String>
)
