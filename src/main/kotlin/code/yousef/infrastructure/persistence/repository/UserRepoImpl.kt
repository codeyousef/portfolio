package code.yousef.infrastructure.persistence.repository

import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.domain.repository.UserRepo
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class UserRepoImpl(
    private val sessionFactory: SessionFactory
) : PanacheRepositoryBase<UserEntity, Uuid>, UserRepo {
    override suspend fun findUserByEmail(email: String): UserEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(user: UserDTO): UserEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: UserDTO): UserEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Uuid): Boolean {
        TODO("Not yet implemented")
    }
}