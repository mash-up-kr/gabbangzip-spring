package com.mashup.pic.event.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.event.applicationService.EventApplicationService
import com.mashup.pic.event.applicationService.dto.toServiceRequest
import com.mashup.pic.event.controller.dto.CreateEventRequest
import com.mashup.pic.event.controller.dto.CreateEventResponse
import com.mashup.pic.event.controller.dto.MarkEventVisitedRequest
import com.mashup.pic.event.controller.dto.MarkEventVisitedResponse
import com.mashup.pic.event.controller.dto.UploadImageRequest
import com.mashup.pic.event.controller.dto.UploadImageResponse
import com.mashup.pic.event.controller.dto.toServiceRequest
import com.mashup.pic.security.authentication.UserInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
            eventApplicationService.create(createEventRequest.toServiceRequest(user.id))
        )
    }

    @Operation(summary = "이미지 업로드")
    @PostMapping("/images")
    fun uploadImageOptions(
        @AuthenticationPrincipal user: UserInfo,
        @Valid @RequestBody uploadImageRequest: UploadImageRequest
    ): ApiResponse<UploadImageResponse> {
        return ApiResponse.success(
            eventApplicationService.uploadImages(uploadImageRequest.toServiceRequest(user.id))
        )
    }

    @PutMapping("/visit")
    @Operation(summary = "이벤트 방문 표시")
    fun markVisited(
        @AuthenticationPrincipal user: UserInfo,
        @Valid @RequestBody request: MarkEventVisitedRequest
    ): ApiResponse<MarkEventVisitedResponse> {
        return ApiResponse.success(eventApplicationService.markEventVisit(request.toServiceRequest(user.id)))
    }
}
