package code.yousef.presentation.dto.response

data class AuthResponse(
    val token: String,
    val user: UserResponse
)