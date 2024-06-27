package com.mashup.pic.group.controller.dto

import java.time.LocalDateTime

data class ViewGroupResponse (
    val groups: List<ViewGroupItem>
)

data class ViewGroupItem (
    val groupName: String,
    val keyword: String,
    val keywordFrameUrl: String,
    val keywordColor1: String,
    val keywordColor2: String,
    val groupStatus: String? = null,
    val hasCurrentEvent: Boolean,
    val hasPastEvent: Boolean,
    val recentEventDate: LocalDateTime,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>
)

data class FramedImage(
    val imageUrl: String,
    val frameUrl: String,
)

// TODO: Need to remove
fun sampleViewGroupResponse(): ViewGroupResponse {
    val framedImages1 = listOf(
        FramedImage("image1.jpg", "frame1.png"),
        FramedImage("image2.jpg", "frame2.png"),
        FramedImage("image3.jpg", "frame3.png")
    )
    val framedImages2 = listOf(
        FramedImage("image4.jpg", "frame4.png"),
        FramedImage("image5.jpg", "frame5.png"),
        FramedImage("image6.jpg", "frame6.png")
    )
    val framedImages3 = listOf(
        FramedImage("image7.jpg", "frame7.png"),
        FramedImage("image8.jpg", "frame8.png"),
        FramedImage("image9.jpg", "frame9.png")
    )

    val viewGroupItems = listOf(
        ViewGroupItem(
            groupName = "Group 1",
            keyword = "Keyword 1",
            keywordFrameUrl = "keywordFrame1.png",
            keywordColor1 = "#FFFFFF",
            keywordColor2 = "#000000",
            groupStatus = "Active",
            hasCurrentEvent = true,
            hasPastEvent = false,
            recentEventDate = LocalDateTime.now(),
            cardFrontImageUrl = "frontImage1.jpg",
            cardBackImages = framedImages1
        ),
        ViewGroupItem(
            groupName = "Group 2",
            keyword = "Keyword 2",
            keywordFrameUrl = "keywordFrame2.png",
            keywordColor1 = "#CCCCCC",
            keywordColor2 = "#333333",
            groupStatus = "Inactive",
            hasCurrentEvent = false,
            hasPastEvent = true,
            recentEventDate = LocalDateTime.now().minusDays(7),
            cardFrontImageUrl = "frontImage2.jpg",
            cardBackImages = framedImages2
        ),
        ViewGroupItem(
            groupName = "Group 3",
            keyword = "Keyword 3",
            keywordFrameUrl = "keywordFrame3.png",
            keywordColor1 = "#AAAAAA",
            keywordColor2 = "#555555",
            groupStatus = "Active",
            hasCurrentEvent = true,
            hasPastEvent = true,
            recentEventDate = LocalDateTime.now().minusDays(3),
            cardFrontImageUrl = "frontImage3.jpg",
            cardBackImages = framedImages3
        )
    )

    return ViewGroupResponse(viewGroupItems)
}
