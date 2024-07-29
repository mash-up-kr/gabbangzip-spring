package com.mashup.pic.alarm.controller.dto

import com.mashup.pic.alarm.applicationservice.dto.RegisterTokenServiceRequest
import io.swagger.v3.oas.annotations.media.Schema

data class RegisterTokenRequest (
    @Schema(description = "FCM 토큰")
    val token : String
)

fun RegisterTokenRequest.toServiceRequest(userId: Long): RegisterTokenServiceRequest =
    RegisterTokenServiceRequest(userId, this.token)
