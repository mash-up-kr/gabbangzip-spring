package com.mashup.pic.user.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.security.authentication.UserInfo
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/valid")
class ValidateController {
    @GetMapping
    fun validate(
        @AuthenticationPrincipal user: UserInfo
    ): ApiResponse<String> {
        return ApiResponse.success(user.nickname + " " + user.id)
    }
}
