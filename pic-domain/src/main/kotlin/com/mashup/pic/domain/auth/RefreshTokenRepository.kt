package com.mashup.pic.domain.auth

import org.springframework.data.repository.CrudRepository


interface RefreshTokenRepository : CrudRepository<RefreshToken?, String?> {
    fun findByRefreshToken(refreshToken: String): RefreshToken?
    fun deleteByRefreshToken(refreshToken: String?)
}
