package code.yousef.model

import java.time.LocalDateTime

enum class UserRole {
    ADMIN, CONTRIBUTOR, USER
}

data class User(
    val id: String = "",
    val username: String,
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.USER,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val lastLogin: LocalDateTime? = null,
    val isAccountActive: Boolean = true
) 