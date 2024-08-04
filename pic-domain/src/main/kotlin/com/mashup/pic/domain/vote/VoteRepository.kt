package com.mashup.pic.domain.vote

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VoteRepository : JpaRepository<Vote, Long> {
    fun existsByEventJoinId(id: Long): Boolean

    @Query("SELECT COUNT(DISTINCT v.eventJoinId) FROM Vote v")
    fun countDistinctEventJoinIds(): Int

    fun findAllByEventImageOptionIdIn(imageOptionIds: List<Long>): List<Vote>
}
