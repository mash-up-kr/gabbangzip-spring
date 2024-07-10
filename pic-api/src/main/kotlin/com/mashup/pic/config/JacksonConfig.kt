package com.mashup.pic.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.mashup.pic.jackson.PicObjectMapperBuilderCustomizer
import io.swagger.v3.core.jackson.ModelResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun picObjectMapperBuilderCustomizer(): PicObjectMapperBuilderCustomizer {
        return PicObjectMapperBuilderCustomizer()
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver {
        return ModelResolver(objectMapper)
    }
}
