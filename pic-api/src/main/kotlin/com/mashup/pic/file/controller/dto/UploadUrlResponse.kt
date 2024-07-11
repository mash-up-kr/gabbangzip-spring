package com.mashup.pic.file.controller.dto

data class UploadUrlResponse(val uploadUrl: String, val fileId: String, val urlExpTsMillis: Number) {
    companion object {
        fun from(
            uploadUrl: String,
            fileId: String,
            urlExpTsMillis: Number
        ): UploadUrlResponse {
            return UploadUrlResponse(
                uploadUrl = uploadUrl,
                fileId = fileId,
                urlExpTsMillis = urlExpTsMillis
            )
        }
    }
}
