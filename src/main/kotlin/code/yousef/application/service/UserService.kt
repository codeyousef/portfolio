package code.yousef.application.service

import at.favre.lib.crypto.bcrypt.BCrypt
import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.domain.repository.UserRepo
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class UserService {

    @Inject
    lateinit var userRepo: UserRepo

    fun getAllUsers(): Uni<List<UserEntity>> {
        return userRepo.listAll()
    }

    fun getUserById(id: Long): Uni<UserEntity> {
        return userRepo.findUserById(id)
    }

    fun getUserByUsername(username: String): Uni<UserEntity> {
        return userRepo.findByUsername(username)
    }

    fun createUser(userEntity: UserEntity): Uni<UserEntity> {
        // Hash the password
        val hashedPassword = hashPassword(userEntity.password)
        userEntity.password = hashedPassword

        return userRepo.saveUser(userEntity)
    }

    fun updateUser(id: Long, updatedUserEntity: UserEntity): Uni<UserEntity> {
        return userRepo.findUserById(id)
            .onItem().ifNotNull().transformToUni { existingUser ->
                existingUser.name = updatedUserEntity.name
                existingUser.email = updatedUserEntity.email

                // Only update password if it's changed
                if (updatedUserEntity.password.isNotBlank()) {
                    existingUser.password = hashPassword(updatedUserEntity.password)
                }

                // Admin can change roles
                if (updatedUserEntity.role != null) {
                    existingUser.role = updatedUserEntity.role
                }

                userRepo.saveUser(existingUser)
            }
    }

    fun deleteUser(id: Long): Uni<Boolean> {
        return userRepo.deleteById(id)
    }

    fun authenticate(username: String, password: String): Uni<UserEntity?> {
        return userRepo.findByUsername(username)
            .onItem().ifNotNull().transformToUni { user ->
                val result = BCrypt.verifyer().verify(password.toCharArray(), user.password)

                if (result.verified) {
                    userRepo.updateLastLogin(user)
                        .onItem().transform { user }
                } else {
                    Uni.createFrom().item(null as UserEntity?)
                }
            }
            .onItem().ifNull().continueWith(null as UserEntity?)
    }

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }
}
