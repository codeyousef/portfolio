package code.yousef.presentation.dto.request

import code.yousef.infrastructure.persistence.entity.UserRole

data class CreateUpdateUserRequest(
    val username: String? = null,
    val password: String? = null,
    val name: String? = null,
    val email: String? = null,
    val role: UserRole? = null
)