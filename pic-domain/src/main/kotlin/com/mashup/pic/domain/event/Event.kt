package com.mashup.pic.domain.event

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.group.Group
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
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
