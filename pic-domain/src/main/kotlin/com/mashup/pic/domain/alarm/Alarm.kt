package com.mashup.pic.domain.alarm

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE alarm SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Alarm(
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val body: String,
    @Column(name = "is_read", nullable = false)
    val isRead: Boolean = false
) : BaseEntity()
