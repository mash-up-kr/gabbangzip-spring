package com.mashup.pic.file.applicationservice

import com.mashup.pic.external.aws.s3.FileUploader
import com.mashup.pic.file.controller.dto.UploadUrlResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date
import java.util.UUID

@Service
@Transactional(readOnly = true)
class FileApplicationService(
    private val s3Service: FileUploader
) {
    fun getUploadUrl(extension: String): UploadUrlResponse {
        val fileId = "pic/${UUID.randomUUID()}.$extension"
        val expiration = Date()
        val expirationInMinutes = 10
        val expTimeMillis = expiration.time + expirationInMinutes * 60 * 1000
        expiration.time = expTimeMillis

        /** upload 가능 시간 10분 */
        val uploadUrl = s3Service.generatePreSignedUrl(fileId, expiration)

        return UploadUrlResponse.from(uploadUrl, fileId, expTimeMillis)
    }
}
