package com.mashup.pic.event.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.event.dto.CreateEventRequest
import com.mashup.pic.event.dto.CreateEventResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "이벤트 컨트롤러")
@RestController
@RequestMapping("/api/v1/events")
class EventController {
    @Operation(summary = "이벤트 생성")
    @PostMapping()
    fun createEvent(
        @Valid @RequestBody createEventRequest: CreateEventRequest
    ): ApiResponse<CreateEventResponse> {
        return ApiResponse.success(
            CreateEventResponse(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/default.png",
                "가빵집 회식",
                LocalDateTime.of(2024, 6, 24, 0, 0),
                LocalDateTime.of(2024, 6, 26, 11, 30)
            )
        )
    }
}
