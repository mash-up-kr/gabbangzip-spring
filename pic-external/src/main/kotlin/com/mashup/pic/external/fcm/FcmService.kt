package com.mashup.pic.external.fcm

interface FcmService {
    fun send(
        token: String,
        title: String,
        body: String
    )
}
