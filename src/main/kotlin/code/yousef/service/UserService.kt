package code.yousef.service

import at.favre.lib.crypto.bcrypt.BCrypt
import code.yousef.model.User
import code.yousef.repository.UserRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class UserService {

    @Inject
    lateinit var userRepository: UserRepository

    fun getAllUsers(): Uni<List<User>> {
        return userRepository.listAll()
    }

    fun getUserById(id: Long): Uni<User> {
        return userRepository.findById(id)
    }

    fun getUserByUsername(username: String): Uni<User> {
        return userRepository.findByUsername(username)
    }

    fun createUser(user: User): Uni<User> {
        // Hash the password
        val hashedPassword = hashPassword(user.password)
        user.password = hashedPassword

        return userRepository.saveUser(user)
    }

    fun updateUser(id: Long, updatedUser: User): Uni<User> {
        return userRepository.findById(id)
            .onItem().ifNotNull().transformToUni { existingUser ->
                existingUser.name = updatedUser.name
                existingUser.email = updatedUser.email

                // Only update password if it's changed
                if (updatedUser.password.isNotBlank()) {
                    existingUser.password = hashPassword(updatedUser.password)
                }

                // Admin can change roles
                if (updatedUser.role != null) {
                    existingUser.role = updatedUser.role
                }

                userRepository.saveUser(existingUser)
            }
    }

    fun deleteUser(id: Long): Uni<Boolean> {
        return userRepository.deleteById(id)
    }

    fun authenticate(username: String, password: String): Uni<User?> {
        return userRepository.findByUsername(username)
            .onItem().ifNotNull().transformToUni { user ->
                val result = BCrypt.verifyer().verify(password.toCharArray(), user.password)

                if (result.verified) {
                    userRepository.updateLastLogin(user)
                        .onItem().transform { user }
                } else {
                    Uni.createFrom().item(null as User?)
                }
            }
            .onItem().ifNull().continueWith(null as User?)
    }

    fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }
}
