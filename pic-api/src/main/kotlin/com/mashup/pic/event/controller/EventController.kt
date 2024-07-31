package com.mashup.pic.event.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.event.applicationService.EventApplicationService
import com.mashup.pic.event.applicationService.dto.toServiceRequest
import com.mashup.pic.event.controller.dto.CreateEventRequest
import com.mashup.pic.event.controller.dto.CreateEventResponse
import com.mashup.pic.security.authentication.UserInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "이벤트 컨트롤러")
@RestController
@RequestMapping("/api/v1/events")
class EventController(
    private val eventApplicationService: EventApplicationService
) {
    @Operation(summary = "이벤트 생성")
    @PostMapping
    fun createEvent(
        @AuthenticationPrincipal user: UserInfo,
        @Valid @RequestBody createEventRequest: CreateEventRequest
    ): ApiResponse<CreateEventResponse> {
        return ApiResponse.success(
            eventApplicationService.create(user.id, createEventRequest.toServiceRequest())
        )
    }
}
