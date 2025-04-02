package code.yousef.infrastructure.persistence.repository

import code.yousef.model.User
import code.yousef.repository.UserRepo
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
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import java.util.UUID

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class UserRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val userMapper: UserMapper
) : PanacheRepositoryBase<UserEntity, UUID>, UserRepo {

    val logger: Logger = LoggerFactory.getLogger(UserRepoImpl::class.java)

    private fun Uuid.toJavaUUID(): UUID {
        return UUID.fromString(this.toString())
    }

    override suspend fun findUserById(id: Uuid): User? {
        val user = sessionFactory.withSession {
            findById(id.toJavaUUID())
        }.awaitSuspending()
        return user?.let { userMapper.toModel(it) }
    }

    override suspend fun findByUsername(username: String): User? {
        val user = sessionFactory.withSession {
            find("username = ?1", username).firstResult()
        }.awaitSuspending()
        return user?.let { userMapper.toModel(it) }
    }

    override suspend fun findUserByEmail(email: String): User? {
        val user = sessionFactory.withSession {
            find("email = ?1", email).firstResult()
        }.awaitSuspending()
        return user?.let { userMapper.toModel(it) }
    }

    override suspend fun saveUser(user: User): User {
        val entity = if (user.id.isNotEmpty()) {
            val existingEntity = sessionFactory.withSession {
                findById(UUID.fromString(user.id))
            }.awaitSuspending()

            if (existingEntity != null) {
                userMapper.updateEntity(existingEntity, user)
            } else {
                userMapper.toEntity(user)
            }
        } else {
            userMapper.toEntity(user)
        }

        val savedEntity = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(entity)
            }
        }.awaitSuspending()

        return userMapper.toModel(savedEntity)
    }

    override suspend fun updateLastLogin(user: User): User {
        // Make sure we have a valid ID
        if (user.id.isEmpty()) {
            throw IllegalArgumentException("Cannot update user without ID")
        }

        // Log for debugging
        logger.debug("Updating lastLogin for user ID: {}, username: {}", user.id, user.username)
        
        // Get the entity from database first
        val existingUser = findUserById(Uuid.parse(user.id))

        if (existingUser == null) {
            logger.warn("User entity not found for ID: ${user.id}")
            throw IllegalStateException("User not found in database")
        }

        // Update last login time
        val updatedUser = user.copy(
            lastLogin = Clock.System.now().toString(),
            updatedAt = Clock.System.now().toString()
        )
        
        // Save the updated user
        return saveUser(updatedUser)
    }

    override suspend fun getAllUsers(): List<User> {
        val users = sessionFactory.withSession {
            findAll().list()
        }.awaitSuspending()
        return users.map { user -> userMapper.toModel(user) }
    }

    override suspend fun deleteUser(id: Uuid): Boolean {
        return sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id.toJavaUUID())
            }
        }.awaitSuspending()
    }
}