package com.mashup.pic.fake

import com.mashup.pic.external.aws.s3.FileUploader
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.Date

@Service
@Profile("test")
class FakeFileUploader : FileUploader {
    override fun generatePreSignedUrl(
        objectKey: String,
        expirationInMinutes: Date
    ): String {
        TODO("Not yet implemented")
    }
}
