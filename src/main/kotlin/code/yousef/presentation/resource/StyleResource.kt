package code.yousef.presentation.resource

import code.yousef.infrastructure.template.styles.StylesheetGenerator
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces

@Path("css")
class StyleResource {

    @Inject
    lateinit var stylesheetRegistry: StylesheetGenerator

    @GET
    @Path("/styles.css")
    @Produces("text/css")
    fun getStyles(): String {
        return stylesheetRegistry.generateStyles()
    }
}