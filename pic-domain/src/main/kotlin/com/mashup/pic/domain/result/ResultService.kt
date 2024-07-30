package com.mashup.pic.domain.result

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ResultService {
    fun getResultOfEvent(eventId: Long): ResultDto {
        // TODO: 투표 결과가 가장 높은 4개 순서대로 리스트 반환
        return ResultDto(ArrayList())
    }
}
