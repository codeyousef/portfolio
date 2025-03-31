package code.yousef.infrastructure.persistence.repository

import code.yousef.domain.model.User
import code.yousef.domain.repository.UserRepo
import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.infrastructure.persistence.mapper.UserMapper
import code.yousef.presentation.dto.request.CreateUpdateUserRequest
import code.yousef.presentation.dto.response.UserResponse
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*

@ApplicationScoped
class UserRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val userMapper: UserMapper
) : PanacheRepositoryBase<UserEntity, UUID>, UserRepo {

    val logger: Logger = LoggerFactory.getLogger(UserRepoImpl::class.java)

    override suspend fun findUserById(id: UUID): User? {
        val user = sessionFactory.withSession {
            findById(id)
        }.awaitSuspending()
        return userMapper.toDomain(user)
    }

    override suspend fun findByUsername(username: String): User? {
        val user = sessionFactory.withSession {
            find("username = ?1", username).firstResult()
        }.awaitSuspending()
        return user?.let { userMapper.toDomain(it) }
    }

    override suspend fun findUserByEmail(email: String): User? {
        val user = sessionFactory.withSession {
            find("email = ?1", email).firstResult()
        }.awaitSuspending()
        return user?.let { userMapper.toDomain(it) }
    }

    override suspend fun saveUser(user: User): User {
        val userToSave = UserEntity().apply {
            this.username = user.username
            this.password = user.password
            this.name = user.name
            this.email = user.email
            this.role = user.role
            this.createdAt = user.createdAt
            this.lastLogin = user.lastLogin
        }
        val savedUser = sessionFactory.withSession { session ->
            if (user.id == null) {
                session.withTransaction {
                    persistAndFlush(userToSave)
                }
            } else {
                // For updates
                session.withTransaction {
                    session.merge(userToSave)
                }
            }
        }.awaitSuspending()
        return userMapper.toDomain(savedUser)
    }

    override suspend fun updateLastLogin(user: User): User {
        // Make sure we have a valid ID
        if (user.id == null) {
            throw IllegalArgumentException("Cannot update user without ID")
        }

        // Log for debugging
        logger.debug("Updating lastLogin for user ID: {}, username: {}", user.id, user.username)

        // Get the entity from database first
        val existingUser = findUserById(user.id)

        if (existingUser == null) {
            logger.warn("User entity not found for ID: ${user.id}")
            throw IllegalStateException("User not found in database")
        }

        // Update the entity and persist it
        val updatedUser = sessionFactory.withSession { session ->
            session.withTransaction {
                // Update the lastLogin field
                existingUser.lastLogin = LocalDateTime.now()
                // Use persist instead of merge since we're working with the entity directly
                session.merge(userMapper.toEntity(existingUser))
                    .onItem()
                    .ifNotNull()
                    .transform { existingUser }
            }
        }.awaitSuspending()

        return updatedUser
    }

    override suspend fun getAllUsers(): List<User> {
        val users = sessionFactory.withSession {
            findAll().list()
        }.awaitSuspending()
        return users.map { user -> userMapper.toDomain(user) }
    }

    override suspend fun deleteUser(id: UUID): Boolean {
        return sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id)
            }
        }.awaitSuspending()
    }

    suspend fun toDomain(request: CreateUpdateUserRequest, existingUser: User? = null): User? {
        return findUserByEmail(request.email ?: existingUser?.email ?: "")
    }

    // From User domain model to UserResponse
    fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id ?: UUID.randomUUID(), // Default for null ID
            username = user.username,
            name = user.name,
            email = user.email,
            role = user.role,
            createdAt = user.createdAt.toString(),
            lastLogin = user.lastLogin.toString()
        )
    }

}