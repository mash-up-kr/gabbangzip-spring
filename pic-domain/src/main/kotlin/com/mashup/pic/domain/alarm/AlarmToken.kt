package com.mashup.pic.domain.alarm

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE croup SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class AlarmToken(
    @Column(nullable = false)
    val userId: Long,
    @Column(nullable = false)
    var token: String
) : BaseEntity()
