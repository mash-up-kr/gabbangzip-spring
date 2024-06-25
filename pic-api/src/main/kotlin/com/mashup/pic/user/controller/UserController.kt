package com.mashup.pic.user.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.security.authentication.UserInfo
import com.mashup.pic.user.controller.dto.DeleteUserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저")
@RestController
@RequestMapping("/api/v1/user")
class UserController {
    @Operation(summary = "회원탈퇴")
    @DeleteMapping
    fun withdraw(
        @AuthenticationPrincipal user: UserInfo,
    ): ApiResponse<DeleteUserResponse> {
        return ApiResponse.success(DeleteUserResponse(user.id))
    }
}
