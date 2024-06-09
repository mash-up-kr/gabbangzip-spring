package com.mashup.pic.auth.applicationService

import com.mashup.pic.external.kakao.KakaoClient
import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.auth.applicationService.dto.LoginServiceResponse
import com.mashup.pic.security.jwt.JwtTokenUtil
import com.mashup.pic.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class AuthApplicationService(
        private val userService: UserService,
        private val jwtTokenUtil: JwtTokenUtil,
        private val kakaoClient: KakaoClient
) {

    fun login(request: LoginServiceRequest): LoginServiceResponse {
        // TODO: login and join logic

        return LoginServiceResponse(0,"dd", "Tt", "tt")
    }
}
