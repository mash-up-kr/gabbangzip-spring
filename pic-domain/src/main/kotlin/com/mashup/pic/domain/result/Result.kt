package com.mashup.pic.domain.result

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE result SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Result(
    @Column(nullable = false)
    val eventId: Long,
    @Column(nullable = false)
    val eventImageOptionId: Long,
    @Column(nullable = false)
    val frame: Frame,
    @Column(name = "image_order", nullable = false)
    val imageOrder: Int
) : BaseEntity()
