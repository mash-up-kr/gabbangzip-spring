package com.mashup.pic.domain.event

import com.mashup.pic.common.exception.PicException
import com.mashup.pic.common.exception.PicExceptionType
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UploadService(
    private val eventRepository: EventRepository,
    private val eventJoinRepository: EventJoinRepository,
    private val eventImageOptionRepository: EventImageOptionRepository
) {
    @Transactional
    fun addImageOptions(
        userId: Long,
        eventId: Long,
        imageUrls: List<String>
    ) {
        val event = getEventById(eventId)
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        validateUserImageUpload(event, eventJoin.id)

        val eventImageOptions = imageUrls.map { EventImageOption(eventJoin.id, it) }
        eventImageOptionRepository.saveAll(eventImageOptions)
    }

    @Transactional
    fun markUploaded(
        userId: Long,
        eventId: Long
    ) {
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        eventJoin.uploaded = true
    }

    fun hasEveryoneUploadedImages(eventId: Long): Boolean {
        val eventJoins = getAllEventJoinByEventId(eventId)
        return eventJoins.all { it.uploaded }
    }

    fun hasUserUploaded(
        userId: Long,
        eventId: Long
    ): Boolean {
        val eventJoin = getEventJoinByUserIdAndEventId(userId, eventId)
        return eventImageOptionRepository.existsByEventJoinId(eventJoin.id)
    }

    private fun getAllEventJoinByEventId(eventId: Long): List<EventJoin> {
        return eventJoinRepository.findAllByEventId(eventId)
    }

    private fun getEventJoinByUserIdAndEventId(
        userId: Long,
        eventId: Long
    ): EventJoin {
        return eventJoinRepository.findByUserIdAndEventId(userId, eventId) ?: throw PicException.of(PicExceptionType.NOT_EXIST)
    }

    private fun getEventById(eventId: Long): Event {
        return eventRepository.findByIdOrNull(eventId) ?: throw PicException.of(PicExceptionType.NOT_EXIST, "$eventId 는 없는 이벤트")
    }

    private fun validateUserImageUpload(
        event: Event,
        evenJoinId: Long
    ) {
        if (event.eventStatus != EventStatus.UPLOADING) {
            throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "업로드 기간이 아님")
        }

        if (eventImageOptionRepository.existsByEventJoinId(evenJoinId)) {
            throw PicException.of(PicExceptionType.ARGUMENT_NOT_VALID, "이미 이미지 업로드한 사용자")
        }
    }
}
