package code.yousef.repository

import code.yousef.model.User
import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.time.LocalDateTime

@ApplicationScoped
class UserRepository(private val sessionFactory: SessionFactory) : PanacheRepository<User> {

    fun findByUsername(username: String): Uni<User> {
        return sessionFactory.withSession { find("username", username).firstResult() }
    }

    fun saveUser(user: User): Uni<User> {
        val now = LocalDateTime.now()

        if (user.id == null) {
            user.createdAt = now
        }

        user.updatedAt = now

        return sessionFactory.withSession { persistAndFlush(user) }
    }

    fun updateLastLogin(user: User): Uni<User> {
        user.lastLogin = LocalDateTime.now()
        return sessionFactory.withSession { persistAndFlush(user) }
    }
}
