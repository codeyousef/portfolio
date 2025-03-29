package code.yousef.application.usecase.user

import code.yousef.application.service.UserService
import code.yousef.infrastructure.persistence.entity.UserRole
import code.yousef.presentation.dto.request.CreateUpdateUserRequest
import code.yousef.presentation.dto.response.UserResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class CreateUserUseCase @Inject constructor(
    private val userService: UserService
) {
    suspend fun execute(request: CreateUpdateUserRequest): UserResponse {
        val user = userService.createUser(request)
        return userService.toResponse(user)
    }

    suspend fun createAdminUser(
        username: String,
        password: String,
        name: String,
        email: String
    ): UserResponse {
        val request = CreateUpdateUserRequest(
            username = username,
            password = password,
            name = name,
            email = email,
            role = UserRole.ADMIN
        )

        val user = userService.createUser(request)
        return userService.toResponse(user)
    }

    suspend fun createContributorUser(
        username: String,
        password: String,
        name: String,
        email: String
    ): UserResponse {
        val request = CreateUpdateUserRequest(
            username = username,
            password = password,
            name = name,
            email = email,
            role = UserRole.CONTRIBUTOR
        )

        val user = userService.createUser(request)
        return userService.toResponse(user)
    }
}