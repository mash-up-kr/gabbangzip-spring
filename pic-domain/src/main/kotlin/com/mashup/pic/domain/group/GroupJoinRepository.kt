package com.mashup.pic.domain.group

import com.mashup.pic.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface GroupJoinRepository : JpaRepository<GroupJoin, Long> {
    fun existsByUserIdAndGroupId(
        userId: Long,
        groupId: Long
    ): Boolean

    fun findAllByGroupId(groupId: Long): List<GroupJoin>
}
