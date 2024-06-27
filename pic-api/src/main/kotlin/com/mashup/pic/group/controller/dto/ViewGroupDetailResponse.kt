package com.mashup.pic.group.controller.dto

import java.time.LocalDateTime

data class ViewGroupDetailResponse (
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
    val cardBackImages: List<FramedImage>
)

fun sampleViewGroupDetailResponse(): ViewGroupDetailResponse {
    val framedImages = listOf(
        FramedImage("image1.jpg", "frame1.png"),
        FramedImage("image2.jpg", "frame2.png"),
        FramedImage("image3.jpg", "frame3.png")
    )

    return ViewGroupDetailResponse(
        groupName = "Group 1",
        keyword = "Keyword 1",
        keywordFrameUrl = "keywordFrame1.png",
        keywordColor1 = "#FFFFFF",
        keywordColor2 = "#000000",
        groupStatus = "Active",
        hasCurrentEvent = true,
        hasPastEvent = false,
        updatedImages = true,
        voted = false,
        recentEventDate = LocalDateTime.now(),
        cardFrontImageUrl = "frontImage1.jpg",
        cardBackImages = framedImages
    )
}
