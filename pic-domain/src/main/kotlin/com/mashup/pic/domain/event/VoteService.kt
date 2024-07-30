package com.mashup.pic.domain.event

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import com.mashup.pic.domain.group.GroupJoinRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class VoteService {
    fun hasUserVoted(userId: Long, eventId: Long): Boolean {
        // TODO 투표 했는지 확인하기
        return false
    }

}
