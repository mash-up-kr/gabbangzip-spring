package com.mashup.pic.domain.result

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.event.Event
import com.mashup.pic.domain.event.EventImageOption
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE result SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Result (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_image_option_id")
    val eventImageOption: EventImageOption,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_id")
    val frame: Frame,
    @Column(name = "image_order", nullable = false)
    val imageOrder: Int
) : BaseEntity()
