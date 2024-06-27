package com.mashup.pic.event.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateEventRequest(
    /** 이벤트 한 줄 요약 */
    @field:NotBlank val summary: String,
    /** 이벤트 날짜 */
    @field:NotNull val eventDate: LocalDateTime,
    /** 이벤트 생성자가 입력하는 투표에 사용될 사진들 */
    @field:Size(min = 1, max = 4) val pictures: List<String>,
)
