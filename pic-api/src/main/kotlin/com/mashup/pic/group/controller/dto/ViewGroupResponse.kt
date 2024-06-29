package com.mashup.pic.group.controller.dto

import java.time.LocalDateTime

data class ViewGroupResponse(
    val groups: List<ViewGroupItem>,
)

data class ViewGroupItem(
    val groupName: String,
    val keyword: Keyword,
    val groupStatus: String,
    val hasCurrentEvent: Boolean,
    val hasPastEvent: Boolean,
    val recentEventDate: LocalDateTime,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>,
)

data class FramedImage(
    val imageUrl: String,
    val frameUrl: String,
)

// TODO: Need to remove
fun sampleViewGroupResponse(): ViewGroupResponse {
    val sampleKeyword =
        Keyword("LITTLE_MOIM", "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg", "#FFFF44", "#FF33FF")

    val framedImages1 =
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
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg",
            ),
        )
    val framedImages2 =
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
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg",
            ),
        )
    val framedImages3 =
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
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/system/frames/club.svg",
            ),
        )

    val viewGroupItems =
        listOf(
            ViewGroupItem(
                groupName = "Group 1",
                keyword = sampleKeyword,
                groupStatus = "Active",
                hasCurrentEvent = true,
                hasPastEvent = false,
                recentEventDate = LocalDateTime.now(),
                cardFrontImageUrl = "frontImage1.jpg",
                cardBackImages = framedImages1,
            ),
            ViewGroupItem(
                groupName = "Group 2",
                keyword = sampleKeyword,
                groupStatus = "Inactive",
                hasCurrentEvent = false,
                hasPastEvent = true,
                recentEventDate = LocalDateTime.now().minusDays(7),
                cardFrontImageUrl = "frontImage2.jpg",
                cardBackImages = framedImages2,
            ),
            ViewGroupItem(
                groupName = "Group 3",
                keyword = sampleKeyword,
                groupStatus = "Active",
                hasCurrentEvent = true,
                hasPastEvent = true,
                recentEventDate = LocalDateTime.now().minusDays(3),
                cardFrontImageUrl = "frontImage3.jpg",
                cardBackImages = framedImages3,
            ),
        )

    return ViewGroupResponse(viewGroupItems)
}
