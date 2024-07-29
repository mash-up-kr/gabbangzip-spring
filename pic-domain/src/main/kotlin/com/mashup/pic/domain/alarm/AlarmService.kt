package com.mashup.pic.domain.alarm

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.user.User
import com.mashup.pic.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
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

    @Transactional
    fun registerTokenForUser(userId: Long, token: String) : String {
        val alarmToken = alarmTokenRepository.findByUserId(userId)

        if (alarmToken != null) {
            alarmToken.token = token
            alarmTokenRepository.save(alarmToken)
        } else {
            val newAlarmToken = AlarmToken(userId = userId, token = token)
            alarmTokenRepository.save(newAlarmToken)
        }

        return token
    }
}
