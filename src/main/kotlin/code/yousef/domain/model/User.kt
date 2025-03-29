package code.yousef.domain.model

import code.yousef.infrastructure.persistence.entity.UserRole
import java.time.LocalDateTime
import java.util.*

/**
 * User domain model with business logic
 */
class User(
    val id: UUID? = null,
    val password: String,
    val username: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val lastLogin: LocalDateTime? = null
) {

    fun isAdmin(): Boolean = role == UserRole.ADMIN

    fun canEditContent(): Boolean = role == UserRole.ADMIN || role == UserRole.CONTRIBUTOR

    fun isAccountActive(): Boolean {
        // If user hasn't logged in for 90 days, consider account inactive
        val ninetyDaysAgo = LocalDateTime.now().minusDays(90)
        return lastLogin == null || lastLogin.isAfter(ninetyDaysAgo)
    }

    fun getDisplayName(): String = name.ifBlank { username }

    fun shouldResetPassword(): Boolean {
        // Password should be reset if not updated in the last 90 days
        val ninetyDaysAgo = LocalDateTime.now().minusDays(90)
        return updatedAt.isBefore(ninetyDaysAgo)
    }

    fun withUpdatedLoginTime(): User {
        return User(
            id = id,
            username = username,
            name = name,
            email = email,
            role = role,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now(),
            lastLogin = LocalDateTime.now(),
            password = password
        )
    }
}