package com.mashup.pic.domain.group

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "group_join")
@SQLDelete(sql = "UPDATE group_join SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class GroupJoin(
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    val groupId: Long
) : BaseEntity()
