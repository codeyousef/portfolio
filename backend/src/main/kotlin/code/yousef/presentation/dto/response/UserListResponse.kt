package code.yousef.presentation.dto.response

data class UserListResponse(
    val users: List<UserResponse>,
    val totalCount: Int
)