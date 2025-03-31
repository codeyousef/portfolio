package code.yousef.infrastructure.security

import at.favre.lib.crypto.bcrypt.BCrypt
import code.yousef.infrastructure.persistence.entity.UserEntity
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.IdentityProvider
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.reactive.mutiny.Mutiny

@ApplicationScoped
class SecurityIdentityProvider(private val sessionFactory: Mutiny.SessionFactory) :
    IdentityProvider<UsernamePasswordAuthenticationRequest> {

    override fun getRequestType(): Class<UsernamePasswordAuthenticationRequest> {
        return UsernamePasswordAuthenticationRequest::class.java
    }

    @WithTransaction
    override fun authenticate(
        request: UsernamePasswordAuthenticationRequest,
        context: AuthenticationRequestContext
    ): Uni<SecurityIdentity> {
        val username = request.username
        val password = String(request.password.password)

        return sessionFactory.withSession { session ->
            session.createQuery<UserEntity>(
                "FROM UserEntity WHERE username = :username", UserEntity::class.java
            )
                .setParameter("username", username)
                .singleResultOrNull
        }.onItem().transformToUni { user ->
            if (user != null && user.password?.let { verifyPassword(password, it) } == true) {
                // Update last login time
                user.lastLogin = java.time.LocalDateTime.now()
                return@transformToUni sessionFactory.withSession { session ->
                    session.withTransaction {
                        session.merge(user)
                    }
                }.map { user }
            } else {
                return@transformToUni Uni.createFrom().nullItem()
            }
        }.map { user ->
            if (user == null) {
                throw io.quarkus.security.AuthenticationFailedException()
            }

            val builder = QuarkusSecurityIdentity.builder()
            builder.setPrincipal { user.username }
            builder.addRole(user.role.toString())
            builder.addCredential(request.password)
            builder.build()
        }
    }

    private fun verifyPassword(inputPassword: String, hashedPassword: String): Boolean {
        val result = BCrypt.verifyer().verify(inputPassword.toCharArray(), hashedPassword)
        return result.verified
    }
}