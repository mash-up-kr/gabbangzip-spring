package com.mashup.pic.auth.controller

import com.mashup.pic.auth.applicationService.AuthApplicationService
import com.mashup.pic.auth.controller.dto.LoginRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.common.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증 컨트롤러")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authApplicationService: AuthApplicationService,
) {

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody loginRequest: LoginRequest,
    ): ApiResponse<LoginResponse> {
        return ApiResponse.success(authApplicationService.login(loginRequest.toServiceRequest()))
    }
}
