package com.mashup.pic.auth.applicationService

import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.auth.applicationService.dto.ReissueServiceRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.auth.controller.dto.ReissueResponse
import com.mashup.pic.domain.auth.RefreshTokenService
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
    private val refreshTokenService: RefreshTokenService,
    private val jwtTokenUtil: JwtManager,
    private val idTokenValidator: KakaoIdTokenValidator,
) {
    @Transactional
    fun login(request: LoginServiceRequest): LoginResponse {
        val oAuthId = idTokenValidator.validateAndGetId(request.idToken, request.nickname)
        val user = userService.findUserByOAuthIdOrNull(oAuthId) ?: createUser(oAuthId, request)

        val authToken = jwtTokenUtil.generateAuthToken(user.toUserInfo())
        refreshTokenService.saveToken(user.id, authToken.refreshToken)
        return LoginResponse.from(user, authToken)
    }

    @Transactional
    fun reissueToken(request: ReissueServiceRequest): ReissueResponse {
        val userId = refreshTokenService.validateAndGetUserId(request.refreshToken)
        val user = userService.findUserByUserId(userId)

        val authToken = jwtTokenUtil.generateAuthToken(user.toUserInfo())
        refreshTokenService.updateToken(
            userId = userId,
            originToken = request.refreshToken,
            newToken = authToken.refreshToken
        )
        return ReissueResponse.from(authToken)
    }

    private fun createUser(oAuthId: Long, request: LoginServiceRequest) : User {
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
