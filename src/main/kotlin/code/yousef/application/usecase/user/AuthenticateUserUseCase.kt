package code.yousef.application.usecase.user

import code.yousef.application.service.UserService
import code.yousef.presentation.dto.request.AuthRequest
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.core.NewCookie
import jakarta.ws.rs.core.Response
import java.net.URI

@ApplicationScoped
class AuthenticateUserUseCase @Inject constructor(
    private val userService: UserService
) {
    suspend fun execute(authRequest: AuthRequest): Response {
        val userEntity = userService.authenticate(authRequest)

        return if (userEntity != null) {
            // Create a session cookie
            val sessionCookie = NewCookie.Builder("session")
                .value(authRequest.username)
                .path("/")
                .maxAge(3600) // 1 hour
                .httpOnly(true)
                .build()

            Response.seeOther(URI.create("/admin/dashboard"))
                .cookie(sessionCookie)
                .build()
        } else {
            Response.seeOther(URI.create("/login?error=Invalid+username+or+password"))
                .build()
        }
    }

    suspend fun validateSession(username: String): Boolean {
        val user = userService.getUserByUsername(username) ?: return false
        return user.isAccountActive
    }

    suspend fun logout(): Response {
        // Clear the session cookie
        val clearedCookie = NewCookie.Builder("session")
            .value("")
            .path("/")
            .maxAge(0)
            .httpOnly(true)
            .build()

        return Response.seeOther(URI.create("/login"))
            .cookie(clearedCookie)
            .build()
    }
}