package com.mashup.pic.auth.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.mashup.pic.auth.applicationService.AuthApplicationService
import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.auth.controller.dto.LoginRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.security.authentication.UserInfo
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
        private val authApplicationService: AuthApplicationService,
) {

    @PostMapping("/login")
    fun login(
            @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<LoginResponse> {
        val serviceRequest: LoginServiceRequest = loginRequest.toServiceRequest()
        return ResponseEntity.ok(LoginResponse.from(authApplicationService.login(serviceRequest)))
    }
}
