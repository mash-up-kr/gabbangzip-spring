package com.mashup.pic.auth.applicationService

import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.domain.user.User
import com.mashup.pic.domain.user.UserService
import com.mashup.pic.security.authentication.UserInfo
import com.mashup.pic.security.jwt.JwtManager
import com.mashup.pic.security.oidc.KakaoIdTokenValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthApplicationService(
    private val userService: UserService,
    private val jwtTokenUtil: JwtManager,
    private val idTokenValidator: KakaoIdTokenValidator,
) {
    @Transactional
    fun login(request: LoginServiceRequest): LoginResponse {
        val oAuthId = idTokenValidator.validateAndGetId(request.idToken, request.nickname)
        val user = userService.findUserByOAuthIdOrNull(oAuthId) ?: createUser(oAuthId, request)

        val authToken = jwtTokenUtil.generateAuthToken(user.toUserInfo())
        return LoginResponse.from(user, authToken)
    }

    private fun createUser(
        oAuthId: Long,
        request: LoginServiceRequest,
    ): User {
        return userService.create(
            oAuthId = oAuthId,
            nickname = request.nickname,
            profileImage = request.profileImage,
        )
    }

    fun User.toUserInfo(): UserInfo {
        return UserInfo(
            id = this.id,
            nickname = this.nickname,
            roles = this.roles,
        )
    }
}
