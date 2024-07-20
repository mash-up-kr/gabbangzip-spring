package com.mashup.pic.jackson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PicObjectMapperBuilderCustomizer : Jackson2ObjectMapperBuilderCustomizer {
    override fun customize(jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder) {
        jacksonObjectMapperBuilder
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .serializerByType(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .serializerByType(LocalDate::class.java, LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
            .serializerByType(LocalTime::class.java, LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME))
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    }
}
