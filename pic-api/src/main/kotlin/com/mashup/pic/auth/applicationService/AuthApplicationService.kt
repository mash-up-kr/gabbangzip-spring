package com.mashup.pic.auth.applicationService

import com.mashup.pic.external.kakao.KakaoClient
import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.auth.applicationService.dto.LoginServiceResponse
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
    fun login(request: LoginServiceRequest): LoginServiceResponse {
        val oAuthId = kakaoClient.getAccessTokenPayload(request.kakaoAccessToken)
        val user = userService.findUserByOAuthId(oAuthId)?: createUser(request.kakaoAccessToken)

        val authToken = jwtTokenUtil.generateAuthToken(convertToUserInfo(user))
        return LoginServiceResponse.from(user, authToken)
    }

    private fun createUser(accessToken: String) : User {
        val userInfo = kakaoClient.getUserInfo(accessToken)
        return userService.create(
                oAuthId = userInfo.id,
                nickname = userInfo.properties.nickname
        )
    }

    private fun convertToUserInfo(user: User): UserInfo {
        return UserInfo(
                id = user.id,
                nickname = user.nickname,
                roles = user.roles
        )
    }
}
