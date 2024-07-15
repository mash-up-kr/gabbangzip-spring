package com.mashup.pic.domain.group

data class KeywordDto(
    val id: Long,
    val name: String
)

fun Keyword.toDto(): KeywordDto = KeywordDto(id, name)
