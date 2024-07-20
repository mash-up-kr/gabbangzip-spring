package com.mashup.pic.external.aws.s3

import java.util.Date

interface FileUploader {
    fun generatePreSignedUrl(
        objectKey: String,
        expirationInMinutes: Date
    ): String
}
