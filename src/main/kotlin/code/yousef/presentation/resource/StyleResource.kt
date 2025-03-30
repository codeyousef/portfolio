package code.yousef.presentation.resource

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Response
import java.io.InputStream
import org.jboss.logging.Logger

@Path("/css")
@ApplicationScoped
class StyleResource {
    
    private val logger = Logger.getLogger(StyleResource::class.java)
    
    /**
     * Serves the static CSS file
     */
    @GET
    @Path("/styles.css")
    @Produces("text/css")
    fun getStaticCss(): Response {
        try {
            logger.info("Serving CSS from StyleResource")
            val staticCssStream: InputStream? = this::class.java.classLoader
                .getResourceAsStream("META-INF/resources/css/styles.css")
            
            if (staticCssStream != null) {
                // Return the static CSS if it exists
                logger.info("Serving static CSS file")
                val css = staticCssStream.bufferedReader().use { it.readText() }
                return Response.ok(css)
                    .header("Content-Type", "text/css;charset=UTF-8")
                    .build()
            } else {
                // Return error if static CSS file is missing
                logger.error("Static CSS file not found")
                return Response.serverError()
                    .entity("Static CSS file not found")
                    .build()
            }
        } catch (e: Exception) {
            logger.error("Error serving CSS", e)
            return Response.serverError()
                .entity("Error serving CSS: ${e.message}")
                .build()
        }
    }
}
