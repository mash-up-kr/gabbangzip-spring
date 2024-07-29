package com.mashup.pic.domain.event

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.user.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "event_join")
@SQLDelete(sql = "UPDATE event_join SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class EventJoin(
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val eventId: Long,
    @Column(nullable = false)
    val isVisited: Boolean = false
) : BaseEntity()
