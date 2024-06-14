package com.mashup.pic.auth.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.mashup.pic.auth.applicationService.AuthApplicationService
import com.mashup.pic.auth.controller.dto.LoginRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.common.ApiResponse
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
        private val authApplicationService: AuthApplicationService,
) {

    @PostMapping("/login")
    fun login(
            @Valid @RequestBody loginRequest: LoginRequest
    ): ApiResponse<LoginResponse> {
        return ApiResponse.success(authApplicationService.login(loginRequest.toServiceRequest()))
    }

}
