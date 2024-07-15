package com.mashup.pic.domain.group

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class KeywordService(private val keywordRepository: KeywordRepository) {
    fun findById(id: Long): KeywordDto {
        return keywordRepository.findByIdOrNull(id)?.toDto()
            ?: throw PicException.of(PicExceptionType.NOT_EXIST, "$id 에 해당하는 Keyword를 찾을 수 없습니다.")
    }
}
