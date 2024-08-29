package com.mashup.pic.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByOAuthId(oauthId: String): User?

    fun findAllByIdIn(ids: List<Long>): List<User>
}
