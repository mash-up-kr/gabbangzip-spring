package com.mashup.pic.alarm.applicationservice

import com.mashup.pic.alarm.applicationservice.dto.KookResponse
import com.mashup.pic.alarm.applicationservice.dto.KookServiceRequest
import com.mashup.pic.alarm.applicationservice.dto.RegisterTokenServiceRequest
import com.mashup.pic.alarm.controller.dto.RegisterTokenResponse
import com.mashup.pic.domain.alarm.AlarmService
import com.mashup.pic.external.fcm.FcmService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AlarmApplicationService(
    private val fcmService: FcmService,
    private val alarmService: AlarmService
) {
    @Transactional
    fun registerAlarmToken(request: RegisterTokenServiceRequest): RegisterTokenResponse {
        val newToken =
            alarmService.registerTokenForUser(
                userId = request.userId,
                token = request.token
            )
        return RegisterTokenResponse(newToken)
    }

    @Transactional
    fun kook(request: KookServiceRequest): KookResponse {
        val targetUserIds = alarmService.getKookTargetUserIds(request.eventId)
        val targetTokens = alarmService.findTokensByUserIds(targetUserIds)
        val alarmMessage = alarmService.generateKookMessage(request.eventId)

        targetTokens.forEach { token ->
            fcmService.send(token, alarmMessage.title, alarmMessage.body)
        }

        targetUserIds.forEach { userId ->
            alarmService.saveAlarm(userId, alarmMessage.title, alarmMessage.body)
        }

        return KookResponse(request.eventId)
    }
}
