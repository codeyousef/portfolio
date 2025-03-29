package code.yousef.presentation.dto.response

import code.yousef.infrastructure.persistence.entity.UserEntity

data class LoginResponse(
    val token: String,
    val userEntity: UserEntity
)
