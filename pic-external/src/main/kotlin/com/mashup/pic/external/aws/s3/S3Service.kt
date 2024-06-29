package com.mashup.pic.external.aws.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.stereotype.Service
import java.util.Date

@Service
class S3Service(
    private val amazonS3: AmazonS3,
    private val s3Properties: S3Config.S3Properties
) {
    fun generatePresignedUrl(
        objectKey: String,
        expirationInMinutes: Int
    ): String {
        val expiration = Date()
        val expTimeMillis = expiration.time + expirationInMinutes * 60 * 1000
        expiration.time = expTimeMillis

        val generatePresignedUrlRequest =
            GeneratePresignedUrlRequest(s3Properties.bucket, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)

        val url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)

        return url.toString()
    }
}
