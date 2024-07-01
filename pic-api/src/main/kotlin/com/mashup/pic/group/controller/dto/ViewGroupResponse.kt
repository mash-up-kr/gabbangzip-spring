package com.mashup.pic.group.controller.dto

import com.mashup.pic.domain.result.Frame
import java.time.LocalDateTime

data class ViewGroupResponse(
    val groups: List<ViewGroupItem>
)

data class ViewGroupItem(
    val name: String,
    val keyword: GroupKeyword,
    val status: Status,
    val statusDescription: String,
    val recentEventDate: LocalDateTime,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>
)

data class FramedImage(
    val imageUrl: String,
    val frame: Frame
)

enum class Status {
    NO_PAST_AND_CURRENT_EVENT,
    NO_CURRENT_EVENT,
    BEFORE_MY_UPLOAD,
    AFTER_MY_UPLOAD,
    BEFORE_MY_VOTE,
    AFTER_MY_VOTE,
    EVENT_COMPLETED
}

// TODO: Need to remove
fun sampleViewGroupResponse(): ViewGroupResponse {
    val sampleKeyword =
        GroupKeyword.CREW

    val framedImages1 =
        listOf(
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                Frame.PLUS
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                Frame.SEXY
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                Frame.GHOST
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                Frame.CLOVER
            )
        )
    val framedImages2 =
        listOf(
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                Frame.HAMBURGER
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                Frame.SNOWMAN
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                Frame.CLOVER
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                Frame.HAMBURGER
            )
        )
    val framedImages3 =
        listOf(
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                Frame.SNOWMAN
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                Frame.GHOST
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                Frame.FLOWER
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                Frame.GHOST
            )
        )

    val viewGroupItems =
        listOf(
            ViewGroupItem(
                name = "Group 1",
                keyword = sampleKeyword,
                status = Status.EVENT_COMPLETED,
                statusDescription = "쉿, 투표중",
                recentEventDate = LocalDateTime.now(),
                cardFrontImageUrl = "frontImage1.jpg",
                cardBackImages = framedImages1
            ),
            ViewGroupItem(
                name = "Group 2",
                keyword = sampleKeyword,
                status = Status.NO_PAST_AND_CURRENT_EVENT,
                statusDescription = "2일전 업데이트",
                recentEventDate = LocalDateTime.now().minusDays(7),
                cardFrontImageUrl = "frontImage2.jpg",
                cardBackImages = framedImages2
            ),
            ViewGroupItem(
                name = "Group 3",
                keyword = sampleKeyword,
                status = Status.BEFORE_MY_VOTE,
                statusDescription = "일주일전 업데이트",
                recentEventDate = LocalDateTime.now().minusDays(3),
                cardFrontImageUrl = "frontImage3.jpg",
                cardBackImages = framedImages3
            )
        )

    return ViewGroupResponse(viewGroupItems)
}
