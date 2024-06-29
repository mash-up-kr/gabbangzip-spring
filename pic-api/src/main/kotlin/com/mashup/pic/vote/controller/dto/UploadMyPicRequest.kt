package com.mashup.pic.vote.controller.dto

import jakarta.validation.constraints.Size

data class UploadMyPicRequest(
    val eventId: String,
    @field:Size(min = 1, max = 4)
    val picImageUrls: List<String>
)
