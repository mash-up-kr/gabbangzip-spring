package com.mashup.pic.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByOauthId(oauthId: Long): Boolean
}
