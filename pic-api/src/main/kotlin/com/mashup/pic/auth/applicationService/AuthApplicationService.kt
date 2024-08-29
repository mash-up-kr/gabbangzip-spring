package com.mashup.pic.auth.applicationService

import com.mashup.pic.auth.applicationService.dto.AppleLoginServiceRequest
import com.mashup.pic.auth.applicationService.dto.LoginServiceRequest
import com.mashup.pic.auth.applicationService.dto.ReissueServiceRequest
import com.mashup.pic.auth.controller.dto.LoginResponse
import com.mashup.pic.auth.controller.dto.ReissueResponse
import com.mashup.pic.domain.auth.RefreshTokenService
import com.mashup.pic.domain.user.UserDto
import com.mashup.pic.domain.user.UserService
import com.mashup.pic.security.authentication.UserInfo
import com.mashup.pic.security.jwt.JwtManager
import com.mashup.pic.security.oidc.AppleIdTokenValidator
import com.mashup.pic.security.oidc.KakaoIdTokenValidator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthApplicationService(
    private val userService: UserService,
    private val refreshTokenService: RefreshTokenService,
    private val jwtManager: JwtManager,
    private val kakaoIdTokenValidator: KakaoIdTokenValidator,
    private val appleIdTokenValidator: AppleIdTokenValidator
) {
    @Transactional
    fun login(request: LoginServiceRequest): LoginResponse {
        val oAuthId = kakaoIdTokenValidator.validateAndGetId(request.idToken, request.nickname)
        val user = userService.findUserByOAuthIdOrNull(oAuthId) ?: createUser(oAuthId, request)

        val authToken = jwtManager.generateAuthToken(user.toUserInfo())
        refreshTokenService.saveToken(user.id, authToken.refreshToken)
        return LoginResponse.from(user, authToken)
    }

    @Transactional
    fun appleLogin(request: AppleLoginServiceRequest): LoginResponse? {
        val oAuthId = appleIdTokenValidator.validateAndGetId(request.idToken, request.user)
        val user = userService.findUserByOAuthIdOrNull(oAuthId) ?: createUser(oAuthId, request)

        val authToken = jwtManager.generateAuthToken(user.toUserInfo())
        refreshTokenService.saveToken(user.id, authToken.refreshToken)
        return LoginResponse.from(user, authToken)
    }

    @Transactional
    fun reissueToken(request: ReissueServiceRequest): ReissueResponse {
        val userId = refreshTokenService.validateAndGetUserId(request.refreshToken)
        val user = userService.findUserByUserId(userId)

        val authToken = jwtManager.generateAuthToken(user.toUserInfo())
        refreshTokenService.updateToken(
            userId = userId,
            originToken = request.refreshToken,
            newToken = authToken.refreshToken
        )
        return ReissueResponse.from(authToken)
    }

    private fun createUser(
        oAuthId: String,
        request: LoginServiceRequest
    ): UserDto {
        return userService.create(
            oAuthId = oAuthId,
            provider = request.provider,
            nickname = request.nickname,
            profileImage = request.profileImage
        )
    }

    private fun createUser(
        oAuthId: String,
        request: AppleLoginServiceRequest
    ): UserDto {
        return userService.create(
            oAuthId = oAuthId,
            provider = request.provider,
            nickname = request.fullName,
            profileImage = DEFAULT_PROFILE_IMAGE
        )
    }

    fun UserDto.toUserInfo(): UserInfo {
        return UserInfo(
            id = this.id,
            nickname = this.nickname,
            roles = this.roles
        )
    }

    companion object {
        const val DEFAULT_PROFILE_IMAGE = "https://www.testhouse.net/wp-content/uploads/2021/11/default-avatar.jpg"
    }
}
