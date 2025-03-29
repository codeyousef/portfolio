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
import java.util.*

@ApplicationScoped
class UserRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val userMapper: UserMapper
) : PanacheRepositoryBase<UserEntity, UUID>, UserRepo {

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

    override suspend fun saveUser(createUpdateUserRequest: CreateUpdateUserRequest): User {
        val domainUser = userMapper.toDomain(createUpdateUserRequest)
        val savedUser = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(userMapper.toEntity(domainUser))
            }
        }.awaitSuspending()
        return userMapper.toDomain(savedUser)
    }

    override suspend fun updateLastLogin(user: User): User {

        val updatedUser = user.withUpdatedLoginTime()
        val savedUser = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(userMapper.toEntity(updatedUser))
            }
        }.awaitSuspending()
        return userMapper.toDomain(savedUser)
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