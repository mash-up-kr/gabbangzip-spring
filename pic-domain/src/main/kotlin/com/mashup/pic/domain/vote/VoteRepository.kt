package com.mashup.pic.domain.vote

import org.springframework.data.jpa.repository.JpaRepository

interface VoteRepository : JpaRepository<Vote, Long> {
    fun existsByEventJoinId(id: Long): Boolean
}
