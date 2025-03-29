package code.yousef.infrastructure.persistence.repository

import code.yousef.domain.model.User
import code.yousef.domain.repository.UserRepo
import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.infrastructure.persistence.mapper.UserMapper
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

    override suspend fun saveUser(user: User): User {
        val savedUser = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(userMapper.toEntity(user))
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
        val deletedUser = sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id)
            }
        }.awaitSuspending()
        return true
    }


}