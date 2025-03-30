package code.yousef.presentation.resource

import code.yousef.infrastructure.template.styles.StylesheetGenerator
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.io.InputStream
import org.jboss.logging.Logger

@Path("/css")
@ApplicationScoped
class StyleResource {

    @Inject
    lateinit var stylesheetRegistry: StylesheetGenerator
    
    private val logger = Logger.getLogger(StyleResource::class.java)
    
    /**
     * Serves the static CSS file first, falling back to dynamically generated CSS
     */
    @GET
    @Path("/styles.css")
    @Produces(MediaType.TEXT_PLAIN)
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
                // Fall back to dynamically generated CSS
                logger.info("Static CSS not found, generating dynamically")
                val css = stylesheetRegistry.generateStyles()
                return Response.ok(css)
                    .header("Content-Type", "text/css;charset=UTF-8")
                    .build()
            }
        } catch (e: Exception) {
            logger.error("Error serving CSS", e)
            return Response.serverError()
                .entity("Error serving CSS: ${e.message}")
                .build()
        }
    }

    /**
     * Always serves dynamically generated CSS
     */
    @GET
    @Path("/dynamic.css")
    @Produces(MediaType.TEXT_PLAIN)
    fun getDynamicCss(): Response {
        try {
            logger.info("CSS endpoint called, generating dynamic styles")
            val css = stylesheetRegistry.generateStyles()
            return Response.ok(css)
                .header("Content-Type", "text/css;charset=UTF-8")
                .build()
        } catch (e: Exception) {
            logger.error("Error generating CSS", e)
            return Response.serverError()
                .entity("Error generating CSS: ${e.message}")
                .build()
        }
    }
}
