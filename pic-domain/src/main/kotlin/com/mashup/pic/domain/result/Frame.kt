package com.mashup.pic.domain.result

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE event_image_choice SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Frame (
    @Column(nullable = false)
    val imageUrl: String
) : BaseEntity()
