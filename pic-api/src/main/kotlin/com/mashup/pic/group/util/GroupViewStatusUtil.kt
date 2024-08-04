package com.mashup.pic.group.util

import com.mashup.pic.domain.event.EventDto
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object GroupViewStatusUtil {
    private const val RECENT_UPDATE = "최근 업데이트가 없습니다."
    private const val VOTING_MESSAGE = "쉿, 투표 중"
    private const val DAYS_AGO_UPDATE = "%d일 전 업데이트"
    private const val WEEK_AGO_UPDATE = "일주일 전 업데이트"
    private const val TWO_WEEKS_AGO_UPDATE = "이주일 전 업데이트"
    private const val NO_RECENT_PIC = "최근 PIC이 없어요"

    fun generateDescription(event: EventDto?): String {
        return if (event != null) {
            val daysSinceUpdate = ChronoUnit.DAYS.between(event.date.toLocalDate(), LocalDateTime.now().toLocalDate())

            when {
                daysSinceUpdate in 1..6 -> DAYS_AGO_UPDATE.format(daysSinceUpdate)
                daysSinceUpdate == 7L -> WEEK_AGO_UPDATE
                daysSinceUpdate in 8..13 -> DAYS_AGO_UPDATE.format(daysSinceUpdate)
                daysSinceUpdate == 14L -> TWO_WEEKS_AGO_UPDATE
                daysSinceUpdate >= 15 -> NO_RECENT_PIC
                else -> RECENT_UPDATE
            }
        } else {
            VOTING_MESSAGE
        }
    }
}
