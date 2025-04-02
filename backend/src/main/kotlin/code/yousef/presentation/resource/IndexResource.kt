package code.yousef.presentation.resource

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import java.io.InputStream
import jakarta.ws.rs.core.Response

/**
 * Resource for serving the app frontend page
 */
@Path("/appui")
class AppUIResource {
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getIndex(): Response {
        val inputStream: InputStream? = javaClass.getResourceAsStream("/META-INF/resources/index.html")
        
        return if (inputStream != null) {
            Response.ok(inputStream).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("Frontend application not found").build()
        }
    }
} 