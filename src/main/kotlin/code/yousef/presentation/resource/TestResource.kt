package code.yousef.presentation.resource

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/test")
class TestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun test(): String {
        return "Test endpoint is working!"
    }

    @GET
    @Path("/css")
    @Produces("text/css")
    fun testCss(): String {
        return """
            /* Test CSS */
            body {
                background-color: #f0f0f0;
                color: #333;
            }
        """.trimIndent()
    }
}
