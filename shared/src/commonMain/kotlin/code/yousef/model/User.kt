package code.yousef.model

enum class UserRole {
    ADMIN, CONTRIBUTOR, USER
}

data class User(
    val id: String,
    val username: String,
    val password: String = "",
    val name: String,
    val email: String,
    val role: UserRole = UserRole.USER,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val lastLogin: String? = null,
    val isAccountActive: Boolean = true
) 