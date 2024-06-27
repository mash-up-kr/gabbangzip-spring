package com.mashup.pic.group.controller

import java.time.LocalDateTime

data class ViewGroupResponse (
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
