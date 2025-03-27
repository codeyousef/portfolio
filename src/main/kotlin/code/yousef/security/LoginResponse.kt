package code.yousef.security

import code.yousef.model.User

data class LoginResponse(
    val token: String,
    val user: User
)
