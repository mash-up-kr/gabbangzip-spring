package com.mashup.pic.fake

import com.mashup.pic.external.fcm.FcmService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("test")
class FakeFcmService : FcmService {
    override fun send(
        token: String,
        title: String,
        body: String
    ) {
        // Do nothing in testing environment
    }
}
