package com.mashup.pic.domain.user

import com.mashup.pic.domain.common.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class User(

    @Column(name = "oauth_id", nullable = false)
    val oAuthId: Long = 0,

    @Column(name = "nickname", nullable = false)
    val nickname: String = "",

    @Column(name = "profileImage", nullable = false)
    val profileImage: String = "",

    @ElementCollection(targetClass = UserRole::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val roles: Set<UserRole> = setOf(UserRole.MEMBER)

) : BaseEntity()
