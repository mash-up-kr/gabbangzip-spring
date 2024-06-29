package com.mashup.pic.domain.alarm

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE alarm SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Alarm(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
    @Column(nullable = false)
    val message: String,
    @Column(name = "is_read", nullable = false)
    val isRead: Boolean = false
) : BaseEntity()
