package code.yousef.infrastructure.persistence.mapper

import code.yousef.domain.model.User
import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.presentation.dto.request.CreateUpdateUserRequest
import code.yousef.presentation.dto.response.UserResponse
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Mapper for converting between User-related classes
 */
@ApplicationScoped
class UserMapper {

    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    /**
     * Convert UserEntity to User domain model
     */
    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            username = entity.username,
            name = entity.name,
            email = entity.email,
            role = entity.role,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            lastLogin = entity.lastLogin,
            password = entity.password
        )
    }

    /**
     * Convert User domain model to UserEntity
     */
    fun toEntity(user: User): UserEntity {
        val entity = UserEntity()
        entity.username = user.username
        entity.name = user.name
        entity.email = user.email
        entity.role = user.role
        entity.createdAt = user.createdAt
        entity.updatedAt = user.updatedAt
        entity.lastLogin = user.lastLogin
        return entity
    }

    /**
     * Update existing UserEntity from User domain model
     */
    fun updateEntity(entity: UserEntity, user: User): UserEntity {
        entity.username = user.username
        entity.name = user.name
        entity.email = user.email
        entity.role = user.role
        entity.updatedAt = LocalDateTime.now()
        entity.lastLogin = user.lastLogin
        return entity
    }

    /**
     * Convert CreateUpdateUserRequest to User domain model
     */
    fun toDomain(request: CreateUpdateUserRequest, existingUser: User? = null): User {
        val now = LocalDateTime.now()
        return User(
            id = existingUser?.id,
            username = request.username ?: existingUser?.username ?: "",
            name = request.name ?: existingUser?.name ?: "",
            email = request.email ?: existingUser?.email ?: "",
            role = request.role ?: existingUser?.role ?: throw IllegalArgumentException("Role must be provided"),
            createdAt = existingUser?.createdAt ?: now,
            updatedAt = now,
            lastLogin = existingUser?.lastLogin,
            password = existingUser.let { existingUser?.password } ?: request.password ?: throw IllegalArgumentException("Password must be provided")
        )
    }

    /**
     * Convert User domain model to UserResponse
     */
    fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id ?: -1,
            username = user.username,
            name = user.name,
            email = user.email,
            role = user.role,
            createdAt = user.createdAt.format(dateFormatter),
            lastLogin = user.lastLogin?.format(dateFormatter)
        )
    }
}