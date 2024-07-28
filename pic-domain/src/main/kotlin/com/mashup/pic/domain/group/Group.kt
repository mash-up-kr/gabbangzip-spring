package com.mashup.pic.domain.group

import com.mashup.pic.domain.common.BaseEntity
import com.mashup.pic.domain.event.Event
import com.mashup.pic.domain.event.EventJoin
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "croup")
@SQLDelete(sql = "UPDATE croup SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class Group(
    @Column(nullable = false)
    val name: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val keyword: GroupKeyword,
    @Column(nullable = false)
    val imageUrl: String,
    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val groupJoins: List<GroupJoin> = listOf(),
) : BaseEntity()
