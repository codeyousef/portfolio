package code.yousef.infrastructure.security

import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.IdentityProvider
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.request.TrustedAuthenticationRequest
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.reactive.mutiny.Mutiny

@ApplicationScoped
class TrustedIdentityProvider(private val sessionFactory: Mutiny.SessionFactory) :
    IdentityProvider<TrustedAuthenticationRequest> {

    override fun getRequestType(): Class<TrustedAuthenticationRequest> {
        return TrustedAuthenticationRequest::class.java
    }

    @WithTransaction
    override fun authenticate(
        request: TrustedAuthenticationRequest,
        context: AuthenticationRequestContext
    ): Uni<SecurityIdentity> {
        val principal = request.principal
        val username = principal.toString() // Get the username as a string

        // Create a builder from the request
        val builder = QuarkusSecurityIdentity.builder()
        builder.setPrincipal { username } // Using lambda to create a Principal

        // Find the user to get their role
        return sessionFactory.withSession { session ->
            session.createQuery<code.yousef.infrastructure.persistence.entity.UserEntity>(
                "FROM UserEntity WHERE username = :username",
                code.yousef.infrastructure.persistence.entity.UserEntity::class.java
            )
                .setParameter("username", username)
                .singleResultOrNull
        }.onItem().transform { user ->
            if (user != null) {
                // Add the role from the database
                builder.addRole(user.role.toString())
            }

            // Build and return the security identity
            builder.build()
        }
    }

}