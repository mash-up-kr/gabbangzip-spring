package com.mashup.pic.domain.event

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@SQLDelete(sql = "UPDATE event SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Event(
    @Column(nullable = false)
    val groupId: Long,
    @Column(nullable = false)
    val description: String,
    @Column(nullable = false)
    val date: LocalDateTime,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var eventStatus: EventStatus = EventStatus.UPLOADING,
    @Column(nullable = true)
    var uploadingEndDate: LocalDateTime? = null,
    @Column(nullable = true)
    var votingEndDate: LocalDateTime? = null
) : BaseEntity()
