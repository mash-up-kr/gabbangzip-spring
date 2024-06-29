package com.mashup.pic.notification.applicationservice

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NotificationApplicationService {
    @Transactional
    fun notifyMembers() {
        TODO("Not yet implemented")
    }
}
