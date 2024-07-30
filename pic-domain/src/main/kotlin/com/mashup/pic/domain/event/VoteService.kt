package com.mashup.pic.domain.event

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class VoteService {
    fun hasUserVoted(
        userId: Long,
        eventId: Long
    ): Boolean {
        // TODO 투표 했는지 확인하기
        return false
    }
}
