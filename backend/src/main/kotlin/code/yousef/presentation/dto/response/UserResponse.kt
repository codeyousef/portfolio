package code.yousef.presentation.dto.response

import code.yousef.infrastructure.persistence.entity.UserRole
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class UserResponse(
    val id: Uuid?,
    val username: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val createdAt: String,
    val lastLogin: String?
)