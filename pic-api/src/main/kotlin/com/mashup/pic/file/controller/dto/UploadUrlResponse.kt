package com.mashup.pic.file.controller.dto

data class UploadUrlResponse(val uploadUrl: String) {
    companion object {
        fun from(uploadUrl: String): UploadUrlResponse {
            return UploadUrlResponse(
                uploadUrl = uploadUrl
            )
        }
    }
}
