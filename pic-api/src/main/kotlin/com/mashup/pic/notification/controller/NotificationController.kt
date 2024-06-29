package com.mashup.pic.notification.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.notification.applicationservice.NotificationApplicationService
import com.mashup.pic.notification.controller.dto.NotifyMembersRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "콕 찌르기")
@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(
    private val notificationApplicationService: NotificationApplicationService,
) {
    @Operation(summary = "쿡 찌르기 API", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping
    fun notifyMembers(
        @RequestBody request: NotifyMembersRequest,
    ): ApiResponse<Unit> {
        return ApiResponse.success(
            notificationApplicationService.notifyMembers(),
        )
    }
}
