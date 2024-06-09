package com.mashup.pic.domain.user

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is NULL")
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(name = "oauth_id")
        val oAuthId: Long = 0,

        @Column(name = "nickname")
        val nickname: String = "",

        @ElementCollection(targetClass = UserRole::class, fetch = FetchType.EAGER)
        @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
        @Enumerated(EnumType.STRING)
        @Column(name = "role")
        val roles: Set<UserRole> = setOf(UserRole.MEMBER),

        @Column(name = "created_at")
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "deleted_at")
        val deletedAt: LocalDateTime? = null
) {
}
