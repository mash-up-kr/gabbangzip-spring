package com.mashup.pic.external.fcm

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FcmServiceImpl(private val firebaseMessaging: FirebaseMessaging) : FcmService {
    private val logger = LoggerFactory.getLogger(FcmServiceImpl::class.java)

    override fun send(
        token: String,
        title: String,
        body: String
    ) {
        val notification =
            Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build()

        val message =
            Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build()

        runCatching {
            firebaseMessaging.send(message)
        }.onSuccess { response ->
            logger.info("Success to send notification : {}", response)
        }.onFailure { e ->
            logger.error("Fail to send notification token: {}, title: {}, body: {}", token, title, body)
        }
    }
}
