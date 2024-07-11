//package com.mashup.pic.domain.event
//
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//@Transactional(readOnly = true)
//class EventService(
//    private val eventRepository: EventRepository
//) {
//    @Transactional
//    fun create(
//        name: String,
//        description: String,
//        imageUrl: String,
//        startDate: String,
//        endDate: String
//    ): EventDto {
//        return eventRepository.save(
//            Event(
//                name = name,
//                description = description,
//                imageUrl = imageUrl,
//                startDate = startDate,
//                endDate = endDate
//            )
//        ).toEventDto()
//    }
//
//    @Transactional
//    fun deleteEvent(eventId: Long) {
//        eventRepository.deleteById(eventId)
//    }
//
//}
