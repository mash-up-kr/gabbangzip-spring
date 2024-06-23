package com.mashup.pic.config

import com.mashup.pic.SharedContext
import org.hamcrest.Matchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.jasypt.encryption.StringEncryptor
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

/**
 * Run > Edit Configurations > Configuration > Environment variables > 'JASYPT_ENCRYPTOR_PASSWORD={암호화키}' 입력
 */
@Disabled("암호화, 복호화 결과 확인을 위한 테스트이므로 비활성화")
@SpringBootTest(classes = [JasyptConfig::class])
class JasyptConfigTest(
    private val stringEncryptor: StringEncryptor
) : SharedContext() {

    private val plainText: String = "test"

    @Test
    fun execute() {
        stringEncryptor.encrypt(plainText).let { encrypted ->
            println(encrypted)
            assertThat(stringEncryptor.decrypt(encrypted), `is`(plainText))
        }
    }
}