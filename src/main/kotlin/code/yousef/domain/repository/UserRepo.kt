package code.yousef.domain.repository

import code.yousef.infrastructure.persistence.entity.UserEntity
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface UserRepo {
    suspend fun findUserById(id: Uuid): UserEntity?
    suspend fun findUserByEmail(email: String): UserEntity?
    suspend fun createUser(user: UserDTO): UserEntity?
    suspend fun updateUser(user: UserDTO): UserEntity?
    suspend fun deleteUser(id: Uuid): Boolean
}
