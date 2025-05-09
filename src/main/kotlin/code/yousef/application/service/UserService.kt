package code.yousef.application.service

import at.favre.lib.crypto.bcrypt.BCrypt
import code.yousef.infrastructure.persistence.mapper.UserMapper
import code.yousef.infrastructure.persistence.repository.UserRepoImpl
import code.yousef.model.User
import code.yousef.model.UserRole
import code.yousef.presentation.dto.request.AuthRequest
import code.yousef.presentation.dto.request.CreateUpdateUserRequest
import code.yousef.presentation.dto.response.UserResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jboss.logging.Logger
import java.time.LocalDateTime
import java.util.*

@ApplicationScoped
class UserService @Inject constructor(
    private val userRepo: UserRepoImpl,
    private val userMapper: UserMapper
) {

    private val logger = Logger.getLogger(UserService::class.java)
    suspend fun getAllUsers(): List<User> {
        return userRepo.getAllUsers()
    }

    suspend fun getUserById(id: UUID): User? {
        return userRepo.findUserById(id)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userRepo.findByUsername(username)
    }

    suspend fun createUser(request: CreateUpdateUserRequest): User? {
        logger.debug(
            "Create user with username: ${request.username}, " +
                    "password hash length: ${request.password?.length ?: 0}"
        )

        val existingUser = userRepo.findByUsername(request.username ?: "")
        if (existingUser != null) {
            throw IllegalArgumentException("Username already exists")
        }

        val now = LocalDateTime.now()

        // Make sure to map the request to a shared model with the password
        val user = request.password?.let {
            User(
                id = "",
                username = request.username ?: "",
                password = it, // This should already be hashed from DataInitializer
                name = request.name ?: "",
                email = request.email ?: "",
                role = request.role?.let { userMapper.mapEntityRoleToSharedRole(it) } ?: UserRole.CONTRIBUTOR,
                createdAt = now,
                updatedAt = now,
                lastLogin = now,
                isAccountActive = true
            )
        }

        return user?.let { userRepo.saveUser(it) }
    }

    suspend fun updateUser(id: UUID, request: CreateUpdateUserRequest): User? {
        val existingUser = userRepo.findUserById(id) ?: return null

        // Check username uniqueness if it's being changed
        if (request.username != null && request.username != existingUser.username) {
            val userWithUsername = userRepo.findByUsername(request.username)
            if (userWithUsername != null) {
                throw IllegalArgumentException("Username already exists")
            }
        }

        val user = userMapper.toModel(request, existingUser)
        return userRepo.saveUser(user)
    }

    suspend fun deleteUser(id: UUID): Boolean {
        return userRepo.deleteUser(id)
    }

    suspend fun authenticate(authRequest: AuthRequest): User? {
        val user = userRepo.findByUsername(authRequest.username) ?: return null

        // Verify password (assuming password is already hashed in the repo)
        val passwordVerified = verifyPassword(authRequest.password, user.password)
        if (!passwordVerified) {
            return null
        }

        // Update login time
        return userRepo.updateLastLogin(user)
    }

    private fun verifyPassword(inputPassword: String, hashedPassword: String?): Boolean {
        if (hashedPassword.isNullOrEmpty()) {
            logger.warn("Attempt to verify password against null or empty hash")
            return false
        }

        // Log the first few characters of the hash for debugging
        logger.debug("Verifying password against hash: ${hashedPassword.take(10)}...")

        val result = BCrypt.verifyer().verify(inputPassword.toCharArray(), hashedPassword)
        return result.verified
    }

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun toResponse(user: User): UserResponse {
        return userMapper.toResponse(user)
    }
}