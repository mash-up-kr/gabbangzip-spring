package com.mashup.pic.auth.controller

import com.mashup.pic.auth.applicationService.AuthApplicationService
import com.mashup.pic.auth.controller.dto.LoginRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.auth.controller.dto.ReissueRequest
import com.mashup.pic.auth.controller.dto.ReissueResponse
import com.mashup.pic.common.PicApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증/인가")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authApplicationService: AuthApplicationService,
) {
    @SecurityRequirements(value = [])
    @Operation(summary = "로그인", description = "OIDC의 ID토큰으로 로그인")
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody loginRequest: LoginRequest,
    ): PicApiResponse<LoginResponse> {
        return PicApiResponse.success(authApplicationService.login(loginRequest.toServiceRequest()))
    }

    @SecurityRequirements(value = [])
    @Operation(summary = "토큰 재발급", description = "리프레시 토큰으로 액세스 토큰, 리프레시 토큰 재발급")
    @PostMapping("/token")
    fun reissue(
        @Valid @RequestBody reissueRequest: ReissueRequest,
    ): PicApiResponse<ReissueResponse> {
        return PicApiResponse.success(authApplicationService.reissueToken(reissueRequest.toServiceRequest()))
    }
}
