package com.mashup.pic.group.controller.dto

import java.time.LocalDateTime

data class ViewGroupDetailResponse(
    val groupName: String,
    val keyword: String,
    val keywordFrameUrl: String,
    val keywordColor1: String,
    val keywordColor2: String,
    val groupStatus: String? = null,
    val hasCurrentEvent: Boolean,
    val hasPastEvent: Boolean,
    val updatedImages: Boolean,
    val voted: Boolean,
    val recentEventDate: LocalDateTime,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>,
)

fun sampleViewGroupDetailResponse(): ViewGroupDetailResponse {
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
        groupName = "가빨집 모임",
        keyword = "LITTLE_MOIM",
        keywordFrameUrl = "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
        keywordColor1 = "#FFFFFF",
        keywordColor2 = "#000000",
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
