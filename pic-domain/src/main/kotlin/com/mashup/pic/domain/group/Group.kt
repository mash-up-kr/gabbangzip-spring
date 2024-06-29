package com.mashup.pic.domain.group

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLDelete(sql = "UPDATE groups SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Group(
    @Column(nullable = false)
    val name: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", unique = true)
    val keyword: Keyword
) : BaseEntity()
