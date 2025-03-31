package code.yousef.domain.repository

import code.yousef.domain.model.User
import java.util.*

interface UserRepo {
    suspend fun findUserById(id: UUID): User?
    suspend fun findByUsername(username: String): User?
    suspend fun findUserByEmail(email: String): User?
    suspend fun saveUser(createUpdateUserRequest: User): User
    suspend fun updateLastLogin(user: User): User
    suspend fun getAllUsers(): List<User>
    suspend fun deleteUser(id: UUID): Boolean
}
