package com.mashup.pic.group.controller.dto

import com.mashup.pic.domain.group.GroupDto
import com.mashup.pic.domain.group.GroupKeyword
import com.mashup.pic.domain.result.Frame
import java.time.LocalDateTime

data class ViewGroupResponse(
    val groups: List<ViewGroupItem>
)

data class ViewGroupItem(
    val id: Long,
    val name: String,
    val keyword: GroupKeyword,
    val status: GroupViewStatus,
    val statusDescription: String,
    val recentEvent: RecentEvent,
    val cardFrontImageUrl: String,
    val cardBackImages: List<FramedImage>?
)

data class FramedImage(
    val imageUrl: String,
    val frame: Frame
)

data class RecentEvent(
    var name: String? = null,
    var date: LocalDateTime? = null
)

fun GroupDto.toViewGroupItem(
    status: GroupViewStatus,
    statusDescription: String,
    recentEvent: RecentEvent,
    cardFrontImageUrl: String,
    cardBackImages: List<FramedImage>?
): ViewGroupItem {
    return ViewGroupItem(
        id = this.id,
        name = this.name,
        keyword = this.keyword,
        status = status,
        statusDescription = statusDescription,
        recentEvent = recentEvent,
        cardFrontImageUrl = cardFrontImageUrl,
        cardBackImages = cardBackImages
    )
}
