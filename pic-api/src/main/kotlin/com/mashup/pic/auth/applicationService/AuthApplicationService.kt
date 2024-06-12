package com.mashup.pic.auth.applicationService

import com.mashup.pic.external.kakao.KakaoClient
import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.domain.user.User
import com.mashup.pic.security.jwt.JwtTokenUtil
import com.mashup.pic.domain.user.UserService
import com.mashup.pic.security.authentication.UserInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthApplicationService(
        private val userService: UserService,
        private val jwtTokenUtil: JwtTokenUtil,
        private val kakaoClient: KakaoClient
) {

    @Transactional
    fun login(request: LoginServiceRequest): LoginResponse {
        val oAuthId = kakaoClient.getAccessTokenPayload(request.token)
        val user = userService.findUserByOAuthIdOrNull(oAuthId)?: createUser(request.token)

        val authToken = jwtTokenUtil.generateAuthToken(user.toUserInfo())
        return LoginResponse.from(user, authToken)
    }

    private fun createUser(token: String) : User {
        val userInfo = kakaoClient.getUserInfo(token)
        return userService.create(
                oAuthId = userInfo.id,
                nickname = userInfo.properties.nickname
        )
    }

    fun User.toUserInfo(): UserInfo {
        return UserInfo(
                id = this.id,
                nickname = this.nickname,
                roles = this.roles
        )
    }

}
