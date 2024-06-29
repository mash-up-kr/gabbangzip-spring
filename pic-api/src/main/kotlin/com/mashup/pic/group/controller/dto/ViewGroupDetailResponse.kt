package com.mashup.pic.group.controller.dto

import java.time.LocalDateTime

data class ViewGroupDetailResponse(
    val groupName: String,
    val keyword: Keyword,
    val groupStatus: String? = null,
    val hasCurrentEvent: Boolean,
    val hasPastEvent: Boolean,
    val updatedImages: Boolean,
    val voted: Boolean,
    val recentEventDate: LocalDateTime,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>,
)

data class Keyword(
    val id: Long,
    val name: String,
)

fun sampleViewGroupDetailResponse(): ViewGroupDetailResponse {
    val sampleKeyword =
        Keyword(1, "LITTLE_MOIM")
    val framedImages =
        listOf(
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg",
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg",
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg",
            ),
        )

    return ViewGroupDetailResponse(
        groupName = "가빵집 모임",
        keyword = sampleKeyword,
        groupStatus = "Active",
        hasCurrentEvent = true,
        hasPastEvent = false,
        updatedImages = true,
        voted = false,
        recentEventDate = LocalDateTime.now(),
        cardFrontImageUrl = "frontImage1.jpg",
        cardBackImages = framedImages,
    )
}
