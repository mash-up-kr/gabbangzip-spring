package com.mashup.pic.file.applicationservice

import com.mashup.pic.external.aws.s3.S3Service
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class FileApplicationService(
    private val s3Service: S3Service,
) {
    fun getUploadUrl(extension: String): String {
        val objectKey = "pic/${UUID.randomUUID()}.${extension}"
        /** upload 가능 시간 10분 */
        return s3Service.generatePresignedUrl(objectKey, 10)
    }
}
