package code.yousef.repository

import code.yousef.model.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface UserRepo {
    suspend fun findUserById(id: Uuid): User?
    suspend fun findByUsername(username: String): User?
    suspend fun findUserByEmail(email: String): User?
    suspend fun saveUser(createUpdateUserRequest: User): User
    suspend fun updateLastLogin(user: User): User
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(id: Uuid): Boolean
} 