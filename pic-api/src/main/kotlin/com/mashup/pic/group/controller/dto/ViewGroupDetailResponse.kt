package com.mashup.pic.group.controller.dto

import java.time.LocalDateTime

data class ViewGroupDetailResponse(
    val name: String,
    val keyword: Keyword,
    val status: String,
    val statusDescription: String,
    val updatedImages: Boolean,
    val piced: Boolean,
    val recentEventDate: LocalDateTime,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>
)

data class Keyword(
    val id: Long,
    val name: String
)

fun sampleViewGroupDetailResponse(): ViewGroupDetailResponse {
    val sampleKeyword =
        Keyword(1, "LITTLE_MOIM")
    val framedImages =
        listOf(
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg"
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg"
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg"
            )
        )

    return ViewGroupDetailResponse(
        name = "가빵집 모임",
        keyword = sampleKeyword,
        status = "Active",
        statusDescription = "2일전 업데이트",
        updatedImages = true,
        piced = false,
        recentEventDate = LocalDateTime.now(),
        cardFrontImageUrl = "frontImage1.jpg",
        cardBackImages = framedImages
    )
}
