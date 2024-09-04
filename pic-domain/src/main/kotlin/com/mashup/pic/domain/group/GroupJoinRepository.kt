package com.mashup.pic.domain.group

import org.springframework.data.jpa.repository.JpaRepository

interface GroupJoinRepository : JpaRepository<GroupJoin, Long> {
    fun existsByUserIdAndGroupId(
        userId: Long,
        groupId: Long
    ): Boolean

    fun findAllByGroupId(groupId: Long): List<GroupJoin>

    fun findAllByUserId(userId: Long): List<GroupJoin>

    fun deleteByUserIdAndGroupId(
        userId: Long,
        groupId: Long
    )
}
