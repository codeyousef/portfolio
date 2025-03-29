package code.yousef.presentation.dto.response

import code.yousef.infrastructure.persistence.entity.UserRole
import java.io.Serializable

data class UserResponse(
    val id: Serializable,
    val username: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val createdAt: String,
    val lastLogin: String?
)