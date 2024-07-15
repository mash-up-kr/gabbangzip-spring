package com.mashup.pic.domain.group

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.domain.IntegrationTestSupport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class KeywordServiceTest : IntegrationTestSupport {
    @Autowired
    private lateinit var keywordService: KeywordService

    @Autowired
    private lateinit var keywordRepository: KeywordRepository

    @DisplayName("id에 해당하는 Keyword를 조회한다.")
    @Test
    fun findById() {
        // given
        val keywordName = "Sample Keyword"
        val keyword = Keyword(keywordName)
        keywordRepository.save(keyword)

        // when
        val findKeyword = keywordService.findById(keyword.id)

        // then
        assertThat(findKeyword).isInstanceOf(KeywordDto::class.java)
        assertThat(findKeyword.id).isEqualTo(keyword.id)
        assertThat(findKeyword.name).isEqualTo(keywordName)
    }

    @DisplayName("조회할 때 name에 해당하는 Keyword가 없다면 실패한다.")
    @Test
    fun findByIdWhenNotExistingKeyword() {
        // given
        val keywordId = 1L

        // when // then
        assertThatThrownBy { keywordService.findById(keywordId) }
            .isInstanceOf(PicException::class.java)
            .hasMessage("$keywordId 에 해당하는 Keyword를 찾을 수 없습니다.")
    }
}
