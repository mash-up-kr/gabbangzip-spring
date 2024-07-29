package com.mashup.pic.alarm.controller

import com.mashup.pic.alarm.applicationservice.AlarmApplicationService
import com.mashup.pic.alarm.controller.dto.RegisterTokenRequest
import com.mashup.pic.alarm.controller.dto.RegisterTokenResponse
import com.mashup.pic.alarm.controller.dto.toServiceRequest
import com.mashup.pic.common.ApiResponse
import com.mashup.pic.security.authentication.UserInfo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "푸시 알림")
@RequestMapping("/api/v1/alarm")
@RestController
class AlarmController(
    private val alarmApplicationService: AlarmApplicationService
) {

    @Operation(summary = "FCM 토큰 등록")
    @PostMapping("/token")
    fun registerTokenForUser(
        @AuthenticationPrincipal user: UserInfo,
        @Valid @RequestBody request: RegisterTokenRequest
    ): ApiResponse<RegisterTokenResponse> {
        return ApiResponse.success(alarmApplicationService.registerAlarmToken(request.toServiceRequest(user.id)))
    }

}
