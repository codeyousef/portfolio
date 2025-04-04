package code.yousef.infrastructure.persistence.repository

import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.infrastructure.persistence.mapper.UserMapper
import code.yousef.model.User
import code.yousef.repository.UserRepo
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
        val existingUser = findUserById(UUID.fromString(user.id))

        if (existingUser == null) {
            logger.warn("User entity not found for ID: ${user.id}")
            throw IllegalStateException("User not found in database")
        }

        // Update last login time
        val updatedUser = user.copy(
            lastLogin = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
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

    override suspend fun deleteUser(id: UUID): Boolean {
        return sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id)
            }
        }.awaitSuspending()
    }
}