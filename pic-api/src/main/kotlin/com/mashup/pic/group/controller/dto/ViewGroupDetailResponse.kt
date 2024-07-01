package com.mashup.pic.group.controller.dto

import com.mashup.pic.domain.result.Frame
import java.time.LocalDateTime

data class ViewGroupDetailResponse(
    val id: Long,
    val name: String,
    val keyword: GroupKeyword,
    val status: Status,
    val statusDescription: String,
    val recentEventDate: LocalDateTime,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>,
    val history: List<HistoryItem>
)

data class HistoryItem(
    val id: Long,
    val name: String,
    val date: LocalDateTime,
    val images: List<FramedImage>
)

fun sampleViewGroupDetailResponse(): ViewGroupDetailResponse {
    val sampleKeyword =
        GroupKeyword.HOBBY
    val framedImages =
        listOf(
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample1.jpeg",
                Frame.GHOST
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample2.jpeg",
                Frame.FLOWER
            ),
            FramedImage(
                "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
                Frame.HAMBURGER
            )
        )

    val sampleHistory =
        listOf(
            HistoryItem(
                id = 1,
                name = "가빵집 모임 #1",
                date = LocalDateTime.of(2023, 4, 1, 10, 0),
                images = framedImages
            ),
            HistoryItem(
                id = 2,
                name = "가빵집 모임 #2",
                date = LocalDateTime.of(2023, 5, 1, 14, 0),
                images = framedImages
            )
        )

    return ViewGroupDetailResponse(
        id = 12,
        name = "가빵집 모임",
        keyword = sampleKeyword,
        status = Status.AFTER_MY_VOTE,
        statusDescription = "2일전 업데이트",
        recentEventDate = LocalDateTime.now(),
        cardFrontImageUrl = "https://pic-api-bucket.s3.ap-northeast-2.amazonaws.com/gbzsample3.jpeg",
        cardBackImages = framedImages,
        history = sampleHistory
    )
}
