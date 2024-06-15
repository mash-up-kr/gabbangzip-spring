package com.mashup.pic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class PicApplication

fun main(args: Array<String>) {
    runApplication<PicApplication>(*args)
}
