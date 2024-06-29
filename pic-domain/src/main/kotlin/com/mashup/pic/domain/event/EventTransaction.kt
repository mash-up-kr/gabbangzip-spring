package com.mashup.pic.domain.event

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.group.Group
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name="event_transaction")
@SQLDelete(sql = "UPDATE event_transaction SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class EventTransaction (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val description: String
) : BaseEntity()
