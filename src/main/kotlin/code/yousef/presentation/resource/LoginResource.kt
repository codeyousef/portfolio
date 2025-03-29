package code.yousef.presentation.resource

import code.yousef.application.service.UserService
import code.yousef.presentation.dto.request.AuthRequest
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.NewCookie
import jakarta.ws.rs.core.Response
import java.net.URI

@Path("/login")
@RequestScoped
class LoginResource {

    @Inject
    lateinit var userService: UserService

    @Location("login.html")
    @Inject
    lateinit var loginTemplate: Template

    @GET
    @Produces(MediaType.TEXT_HTML)
    fun loginPage(@QueryParam("error") error: String?): TemplateInstance {
        return loginTemplate.data("error", error)
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    suspend fun login(
        @FormParam("username") username: String,
        @FormParam("password") password: String
    ): Response {
        val request = AuthRequest(username, password)
        val user = userService.authenticate(request)

        return if (user != null) {
            // Create a session cookie
            val sessionCookie = NewCookie.Builder("session")
                .value(username)
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
}

@GET
@Path("/logout")
@Produces(MediaType.TEXT_HTML)
fun logout(): Response {
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