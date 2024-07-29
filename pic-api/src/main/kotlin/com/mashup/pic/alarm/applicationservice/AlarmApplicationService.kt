package com.mashup.pic.alarm.applicationservice

import com.mashup.pic.alarm.applicationservice.dto.RegisterTokenServiceRequest
import com.mashup.pic.alarm.controller.dto.RegisterTokenResponse
import com.mashup.pic.domain.alarm.AlarmService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AlarmApplicationService (
    private val alarmService: AlarmService
){
    @Transactional
    fun registerAlarmToken(request: RegisterTokenServiceRequest) : RegisterTokenResponse {
        val newToken = alarmService.registerTokenForUser(
            userId = request.userId,
            token = request.token
        )
        return RegisterTokenResponse(newToken)
    }
}
