package com.mashup.pic.external.aws.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.mashup.pic.external.config.S3Config
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.Date

@Service
@Profile("!test")
class S3Service(
    private val amazonS3: AmazonS3,
    private val s3Properties: S3Config.S3Properties
) : FileUploader {
    override fun generatePreSignedUrl(
        objectKey: String,
        expiration: Date
    ): String {
        val generatePresignedUrlRequest =
            GeneratePresignedUrlRequest(s3Properties.bucket, objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration)

        val url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)

        return url.toString()
    }
}
