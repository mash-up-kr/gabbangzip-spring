package com.mashup.pic.group.util

import com.mashup.pic.domain.event.EventDto
import com.mashup.pic.group.controller.dto.GroupViewStatus

object GroupViewStatusUtil {
    fun determineStatus(
        hasCurrentEvent: Boolean,
        hasPastEvent: Boolean,
        uploaded: Boolean,
        voted: Boolean
    ): GroupViewStatus {
        return when {
            !hasCurrentEvent && !hasPastEvent -> GroupViewStatus.NO_PAST_AND_CURRENT_EVENT
            hasCurrentEvent && !hasPastEvent -> GroupViewStatus.NO_CURRENT_EVENT
            !uploaded && !voted -> GroupViewStatus.BEFORE_MY_UPLOAD
            uploaded && !voted -> GroupViewStatus.AFTER_MY_UPLOAD
            uploaded && voted -> GroupViewStatus.BEFORE_MY_VOTE
            else -> GroupViewStatus.AFTER_MY_VOTE
        }
    }

    fun determineStatus(
        hasCurrentEvent: Boolean,
        hasPastEvent: Boolean,
        uploaded: Boolean,
        voted: Boolean,
        visited: Boolean,
    ): GroupViewStatus {
        return when {
            !hasCurrentEvent && !hasPastEvent -> GroupViewStatus.NO_PAST_AND_CURRENT_EVENT
            hasCurrentEvent && !hasPastEvent -> GroupViewStatus.NO_CURRENT_EVENT
            !uploaded && !voted -> GroupViewStatus.BEFORE_MY_UPLOAD
            uploaded && !voted -> GroupViewStatus.AFTER_MY_UPLOAD
            uploaded && voted -> GroupViewStatus.BEFORE_MY_VOTE
            !visited -> GroupViewStatus.AFTER_MY_VOTE
            else -> GroupViewStatus.EVENT_COMPLETED
        }
    }

    // TODO: Update with real parameters
    fun generateDescription(
        event: EventDto?
    ) : String {
        return when {
            event != null -> "최근 업데이트 10일 전"
            else -> "쉿, 투표 중"
        }
    }
}
