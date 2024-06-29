package com.mashup.pic.domain.event

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.event.EventImageOption
import com.mashup.pic.domain.event.EventJoin
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE vote SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Vote (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_join_id")
    val eventJoin: EventJoin,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_image_option_id")
    val eventImageOption: EventImageOption
) : BaseEntity()
