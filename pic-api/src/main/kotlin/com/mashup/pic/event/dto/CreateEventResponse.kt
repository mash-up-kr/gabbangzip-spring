package com.mashup.pic.event.dto

import java.time.LocalDateTime

data class CreateEventResponse(
    /** 기본 이미지 url */
    val imageUrl: String,
    /** 이벤트 한 줄 요역 */
    val summary: String,
    /** 모임을 가졌던 날짜 */
    val eventDate: LocalDateTime,
    /** 사진 올리기가 종료되는 시간 */
    val uploadDueDate: LocalDateTime
)
