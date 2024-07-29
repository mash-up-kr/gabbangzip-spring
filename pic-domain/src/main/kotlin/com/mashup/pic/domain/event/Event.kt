package com.mashup.pic.domain.event

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
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
    val date: LocalDateTime
) : BaseEntity()
