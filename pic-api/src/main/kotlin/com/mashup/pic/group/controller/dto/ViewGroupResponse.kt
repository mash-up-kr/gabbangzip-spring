package com.mashup.pic.group.controller.dto

import java.time.LocalDateTime

data class ViewGroupResponse(
    val groups: List<ViewGroupItem>,
)

data class ViewGroupItem(
    val name: String,
    val keyword: Keyword,
    val status: String,
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
        Keyword(1, "LITTLE_MOIM")

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
                name = "Group 1",
                keyword = sampleKeyword,
                status = "쉿, 투표중",
                hasCurrentEvent = true,
                hasPastEvent = false,
                recentEventDate = LocalDateTime.now(),
                cardFrontImageUrl = "frontImage1.jpg",
                cardBackImages = framedImages1,
            ),
            ViewGroupItem(
                name = "Group 2",
                keyword = sampleKeyword,
                status = "6일전 업데이트",
                hasCurrentEvent = false,
                hasPastEvent = true,
                recentEventDate = LocalDateTime.now().minusDays(7),
                cardFrontImageUrl = "frontImage2.jpg",
                cardBackImages = framedImages2,
            ),
            ViewGroupItem(
                name = "Group 3",
                keyword = sampleKeyword,
                status = "일주일전 업데이트",
                hasCurrentEvent = true,
                hasPastEvent = true,
                recentEventDate = LocalDateTime.now().minusDays(3),
                cardFrontImageUrl = "frontImage3.jpg",
                cardBackImages = framedImages3,
            ),
        )

    return ViewGroupResponse(viewGroupItems)
}
