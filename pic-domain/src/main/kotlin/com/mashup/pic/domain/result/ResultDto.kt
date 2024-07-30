package com.mashup.pic.domain.result

data class ResultDto(
    val resultImages: List<ResultItem>
)

data class ResultItem(
    val imageUrl: String,
    val frame: Frame
)
