package com.mashup.pic.config

import com.mashup.pic.jackson.PicObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun picObjectMapperBuilderCustomizer(): PicObjectMapperBuilderCustomizer {
        return PicObjectMapperBuilderCustomizer()
    }
}
