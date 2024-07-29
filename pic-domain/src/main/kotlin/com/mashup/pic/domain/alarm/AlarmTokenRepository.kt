package com.mashup.pic.domain.alarm

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlarmTokenRepository : JpaRepository<AlarmToken, Long> {
    fun findAllByUserIdIn(userIds: List<Long>): List<AlarmToken>
}
