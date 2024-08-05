package com.mashup.pic.alarm.controller.dto

import com.mashup.pic.alarm.applicationservice.dto.KookServiceRequest

data class KookRequest(
    val eventId: Long
)

fun KookRequest.toServiceRequest(): KookServiceRequest = KookServiceRequest(this.eventId)
