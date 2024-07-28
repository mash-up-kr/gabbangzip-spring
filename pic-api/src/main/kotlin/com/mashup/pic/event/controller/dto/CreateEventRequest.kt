package com.mashup.pic.event.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateEventRequest(
    val groupId: Long,
    /** 이벤트 한 줄 요약 */
    @field:NotBlank val description: String,
    /** 이벤트 날짜 */
    @field:NotNull val date: LocalDateTime,
    /** 이벤트 생성자가 입력하는 투표에 사용될 사진들 */
    @field:Size(min = 4, max = 4) val pictures: List<String>
)
