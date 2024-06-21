package com.mashup.pic.domain.auth

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
   private val refreshTokenRepository: RefreshTokenRepository
) {

    fun saveToken(userId: Long, token: String) {
        refreshTokenRepository.save(RefreshToken(token, userId));
    }

    fun validateAndGetUserId(token: String): Long {
        val refreshToken = refreshTokenRepository.findByRefreshToken(token)
            ?: throw PicException.of(PicExceptionType.INVALID_USER_AUTH_TOKEN)

        return refreshToken.userId
    }

    fun updateToken(userId: Long, originToken: String, newToken: String) {
        refreshTokenRepository.deleteByRefreshToken(originToken)
        refreshTokenRepository.save(RefreshToken(newToken, userId))
    }

    fun deleteToken(token: String) {
        refreshTokenRepository.deleteByRefreshToken(token)
    }

}
