package com.mashup.pic.group.controller.dto

import com.mashup.pic.domain.group.GroupKeyword
import java.time.LocalDateTime

data class ViewGroupDetailResponse(
    val id: Long,
    val name: String,
    val keyword: GroupKeyword,
    val status: GroupViewStatus,
    val statusDescription: String,
    val recentEvent: RecentEventDetail,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>?,
    val history: List<HistoryItem>
)

data class RecentEventDetail(
    val id: Long = -1,
    val name: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val deadline: LocalDateTime = LocalDateTime.now()
)

data class HistoryItem(
    val id: Long,
    val name: String,
    val date: LocalDateTime,
    val images: List<FramedImage>?
)
