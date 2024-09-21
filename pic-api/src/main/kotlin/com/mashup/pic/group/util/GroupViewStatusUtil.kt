package com.mashup.pic.group.util

import com.mashup.pic.domain.event.EventDto
import com.mashup.pic.domain.event.EventStatus
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object GroupViewStatusUtil {
    private const val PIC_ON_PROGRESS = "쉿 PIC하는 중"
    private const val RECENT_UPDATE = "최근 업데이트"
    private const val DAYS_AGO_UPDATE = "%d일 전 업데이트"
    private const val WEEK_AGO_UPDATE = "일주일 전 업데이트"
    private const val TWO_WEEKS_AGO_UPDATE = "이주일 전 업데이트"
    private const val NO_RECENT_PIC = "최근 PIC이 없어요"

    fun generateDescription(lastEvent: EventDto?): String {
        if (lastEvent != null) {
            if (lastEvent.eventStatus != EventStatus.COMPLETE) {
                return PIC_ON_PROGRESS
            }

            val daysSinceUpdate = ChronoUnit.DAYS.between(lastEvent.createdAt.toLocalDate(), LocalDateTime.now().toLocalDate())
            return when {
                daysSinceUpdate == 0L -> RECENT_UPDATE.format(daysSinceUpdate)
                daysSinceUpdate in 1..6 -> DAYS_AGO_UPDATE.format(daysSinceUpdate)
                daysSinceUpdate == 7L -> WEEK_AGO_UPDATE
                daysSinceUpdate in 8..13 -> DAYS_AGO_UPDATE.format(daysSinceUpdate)
                daysSinceUpdate == 14L -> TWO_WEEKS_AGO_UPDATE
                daysSinceUpdate >= 15 -> NO_RECENT_PIC
                else -> NO_RECENT_PIC
            }
        }
        return NO_RECENT_PIC
    }
}
