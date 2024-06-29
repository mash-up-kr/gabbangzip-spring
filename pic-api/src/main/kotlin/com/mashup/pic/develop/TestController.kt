package com.mashup.pic.develop

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.domain.user.UserRepository
import com.mashup.pic.security.authentication.UserInfo
import com.mashup.pic.security.jwt.JwtManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController(
    private val userRepository: UserRepository,
    private val jwtManager: JwtManager
) {
    @GetMapping("/{userId}")
    fun getAccessToken(
        @PathVariable userId: Long
    ): ApiResponse<String> {
        val user = userRepository.findByIdOrNull(userId)!!
        UserInfo(id = user.id, nickname = user.nickname, roles = user.roles)
        val authToken = jwtManager.generateAuthToken(UserInfo(id = user.id, nickname = user.nickname, roles = user.roles))
        return ApiResponse.success(authToken.accessToken)
    }

    @GetMapping
    fun validate(
        @AuthenticationPrincipal user: UserInfo
    ): ApiResponse<String> {
        return ApiResponse.success(user.nickname + " " + user.id)
    }
}
