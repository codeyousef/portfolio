package code.yousef.application.usecase.user

import code.yousef.application.service.UserService
import code.yousef.presentation.dto.response.UserListResponse
import code.yousef.presentation.dto.response.UserResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID


@ApplicationScoped
class GetUserUseCase @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUserById(id: UUID): UserResponse? {
        val user = userService.getUserById(id) ?: return null
        return userService.toResponse(user)
    }

    suspend fun getUserByUsername(username: String): UserResponse? {
        val user = userService.getUserByUsername(username) ?: return null
        return userService.toResponse(user)
    }

    suspend fun getAllUsers(): UserListResponse {
        val users = userService.getAllUsers()
        val userResponses = users.map { userService.toResponse(it) }
        return UserListResponse(userResponses, users.size)
    }

    suspend fun getActiveUsers(): UserListResponse {
        val users = userService.getAllUsers().filter { it.isAccountActive }
        val userResponses = users.map { userService.toResponse(it) }
        return UserListResponse(userResponses, users.size)
    }
}