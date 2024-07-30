package com.mashup.pic.domain.event

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UploadService {
    fun hasUserUploaded(
        userId: Long,
        eventId: Long
    ): Boolean {
        // TODO: 올린 이미지 있는지 확인하기
        return true
    }
}
