package com.mashup.pic.domain.alarm

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AlarmService(
    private val alarmTokenRepository: AlarmTokenRepository
) {
    fun findTokensByUserIds(userIds: List<Long>): List<String> {
        val tokens = alarmTokenRepository.findAllByUserIdIn(userIds)
        return tokens.map { it.token }
    }
}
