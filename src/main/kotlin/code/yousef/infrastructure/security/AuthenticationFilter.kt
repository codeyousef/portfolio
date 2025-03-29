package code.yousef.infrastructure.security

import code.yousef.application.service.UserService
import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.core.Cookie
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.Provider
import java.net.URI

@Provider
@ApplicationScoped
@Priority(Priorities.AUTHENTICATION)
class AuthenticationFilter : ContainerRequestFilter {

    @Inject
    lateinit var userService: UserService

    override fun filter(requestContext: ContainerRequestContext) {
        val path = requestContext.uriInfo.path
        
        // Skip authentication for public paths and the login page itself
        if (isPublicPath(path)) {
            return
        }
        
        // Check for session cookie
        val cookies = requestContext.cookies
        val sessionCookie: Cookie? = cookies["session"]
        
        if (sessionCookie == null) {
            abortWithRedirect(requestContext)
            return
        }
        
        // Validate username from cookie (basic approach)
        val username = sessionCookie.value
        val user = userService.getUserByUsername(username).await().indefinitely()
        
        if (user == null) {
            abortWithRedirect(requestContext)
        }
        
        // At this point, the user is authenticated
    }
    
    private fun isPublicPath(path: String): Boolean {
        val publicPaths = listOf(
            "/login", 
            "/css", 
            "/js", 
            "/images", 
            "/models", 
            "/uploads", 
            "/.well-known",
            "/",
            "/blog"
        )
        
        return publicPaths.any { path.startsWith(it) }
    }
    
    private fun abortWithRedirect(requestContext: ContainerRequestContext) {
        requestContext.abortWith(
            Response.seeOther(URI.create("/login"))
                .build()
        )
    }
}
