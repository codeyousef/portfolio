package code.yousef.infrastructure.persistence.mapper

import code.yousef.model.User
import code.yousef.model.UserRole
import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.infrastructure.persistence.entity.UserRole as EntityUserRole
import code.yousef.presentation.dto.request.CreateUpdateUserRequest
import code.yousef.presentation.dto.response.UserResponse
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import java.util.UUID

/**
 * Mapper for converting between User-related classes
 */
@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class UserMapper {

    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    /**
     * Convert UserEntity to User model
     */
    fun toModel(entity: UserEntity): User {
        return User(
            id = entity.id.toString(),
            username = entity.username,
            name = entity.name,
            email = entity.email,
            role = mapEntityRoleToSharedRole(entity.role),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            lastLogin = entity.lastLogin
        )
    }

    /**
     * Convert User model to UserEntity
     */
    fun toEntity(user: User): UserEntity {
        val entity = UserEntity()
        if (user.id.isNotEmpty()) {
            entity.id = UUID.fromString(user.id)
        }
        entity.username = user.username
        entity.name = user.name
        entity.email = user.email
        entity.role = mapSharedRoleToEntityRole(user.role)
        entity.createdAt = user.createdAt!!
        entity.updatedAt = user.updatedAt
        entity.lastLogin = user.lastLogin
        entity.password = "" // Default empty password, should be set separately
        return entity
    }

    /**
     * Update existing UserEntity from User model
     */
    fun updateEntity(entity: UserEntity, user: User): UserEntity {
        entity.username = user.username
        entity.name = user.name
        entity.email = user.email
        entity.role = mapSharedRoleToEntityRole(user.role)
        entity.updatedAt = LocalDateTime.now()
        entity.lastLogin = user.lastLogin
        return entity
    }

    private fun parseToJavaLocalDateTime(dateStr: String): LocalDateTime {
        return try {
            LocalDateTime.parse(dateStr, dateFormatter)
        } catch (e: Exception) {
           LocalDateTime.now()
        }
    }

    /**
     * Maps CreateUpdateUserRequest to User, using an existing user as a base if provided.
     * This is for updating an existing user.
     */
    fun toModel(request: CreateUpdateUserRequest, existingUser: User? = null): User {

        return User(
            id = existingUser?.id ?: "",
            username = request.username ?: existingUser?.username ?: "",
            // Only update password if provided, otherwise keep existing
            password = if (request.password.isNullOrBlank()) existingUser?.password ?: "" else request.password,
            name = request.name ?: existingUser?.name ?: "",
            email = request.email ?: existingUser?.email ?: "",
            role = request.role?.let { mapEntityRoleToSharedRole(it) } ?: existingUser?.role ?: UserRole.CONTRIBUTOR,
            createdAt = existingUser?.createdAt,
            updatedAt = LocalDateTime.now(),
            lastLogin = existingUser?.lastLogin,
            isAccountActive = true
        )
    }

    /**
     * Convert User model to UserResponse
     */
    fun toResponse(user: User): UserResponse {
        val uuid = if (user.id.isNotEmpty()) {
            try {
                Uuid.parse(user.id)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
        
        return UserResponse(
            id = uuid,
            username = user.username,
            name = user.name,
            email = user.email,
            role = mapSharedRoleToEntityRole(user.role),
            createdAt = (user.createdAt ?: "").toString(),
            lastLogin = user.lastLogin.toString()
        )
    }

    // Mapping between domain and entity user roles
    public fun mapEntityRoleToSharedRole(role: EntityUserRole): UserRole {
        return when (role) {
            EntityUserRole.ADMIN -> UserRole.ADMIN
            EntityUserRole.CONTRIBUTOR -> UserRole.CONTRIBUTOR
            EntityUserRole.USER -> UserRole.USER
        }
    }

    public fun mapSharedRoleToEntityRole(role: UserRole): EntityUserRole {
        return when (role) {
            UserRole.ADMIN -> EntityUserRole.ADMIN
            UserRole.CONTRIBUTOR -> EntityUserRole.CONTRIBUTOR
            UserRole.USER -> EntityUserRole.USER
        }
    }
}