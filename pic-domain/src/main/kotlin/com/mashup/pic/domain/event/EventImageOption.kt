package com.mashup.pic.domain.event

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "event_image_choice")
@SQLDelete(sql = "UPDATE event_image_choice SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class EventImageOption(
    @Column(nullable = false)
    val eventJoinId: Long,
    @Column(nullable = false)
    val imageUrl: String
) : BaseEntity()
