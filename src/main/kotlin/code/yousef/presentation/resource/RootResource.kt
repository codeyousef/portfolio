package code.yousef.presentation.resource

import code.yousef.application.service.ProjectService
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.qute.QuteComponents
import code.yousef.infrastructure.summon.SummonRenderer
import code.yousef.ui.AppRoot
import code.yousef.ui.HomePage
import io.quarkus.qute.Template
import io.quarkus.qute.Location
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

/**
 * Resource that handles the root path (/) of the application.
 * This resource renders the home page using Summon components.
 */
@Path("/")
class RootResource @Inject constructor(
    private val projectService: ProjectService,
    private val projectMapper: ProjectMapper,
    private val quteComponents: QuteComponents,
    private val summonRenderer: SummonRenderer,
    @Location("index.html")
    private val indexTemplate: Template
) {

    /**
     * Renders the home page using Summon components.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    suspend fun getHomePage(): String {
        System.err.println("CRITICAL: RootResource.getHomePage() called - rendering home page with Summon")
        System.err.println("CRITICAL: SummonRenderer instance: ${summonRenderer.javaClass.name}")
        System.err.println("CRITICAL: ProjectService instance: ${projectService.javaClass.name}")
        System.err.println("CRITICAL: ProjectMapper instance: ${projectMapper.javaClass.name}")

        try {
            System.err.println("CRITICAL: Rendering home page with Summon")

            try {
                // Render the home page using Summon
                System.err.println("CRITICAL: About to render home page with Summon")
                val html = summonRenderer.render(
                    title = "Yousef's Portfolio - Modern Software Development",
                    description = "Showcasing software development projects and services specializing in Kotlin, Quarkus, and modern web technologies."
                ) {
                    AppRoot {
                        HomePage()
                    }
                }

                System.err.println("CRITICAL: RootResource.getHomePage() completed successfully, result length: ${html.length}")
                System.err.println("CRITICAL: First 100 chars of HTML: ${html.take(100)}")
                return html
            } catch (dbException: Exception) {
                // Check if it's a database connection issue
                if (dbException.message?.contains("Connection refused") == true || 
                    dbException.cause?.message?.contains("Connection refused") == true) {
                    System.err.println("CRITICAL: Database connection failed, using fallback rendering")

                    // Render a simplified version of the home page
                    val html = summonRenderer.render(
                        title = "Yousef's Portfolio - Modern Software Development",
                        description = "Showcasing software development projects and services specializing in Kotlin, Quarkus, and modern web technologies."
                    ) {
                        AppRoot {
                            HomePage()
                        }
                    }

                    System.err.println("CRITICAL: Fallback rendering completed successfully, result length: ${html.length}")
                    return html
                } else {
                    // Re-throw if it's not a connection issue
                    throw dbException
                }
            }
        } catch (e: Exception) {
            System.err.println("CRITICAL ERROR rendering home page with Summon: ${e}")
            System.err.println("CRITICAL: Exception type: ${e.javaClass.name}")
            System.err.println("CRITICAL: Exception message: ${e.message}")
            System.err.println("CRITICAL: Stack trace: ${e.stackTraceToString()}")
            return "<h1>Error rendering home page</h1><p>${e.message}</p><pre>${e.stackTraceToString()}</pre>"
        }
    }

    /**
     * Renders a simple HTML page without using Qute templates.
     * This is useful for testing if the basic routing is working.
     */
    @GET
    @Path("/direct-html")
    @Produces(MediaType.TEXT_HTML)
    fun getDirectHtml(): String {
        System.err.println("CRITICAL: RootResource.getDirectHtml() called - rendering direct HTML")

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Direct HTML Test</title>
                <style>
                    body { 
                        font-family: system-ui, sans-serif; 
                        line-height: 1.5;
                        padding: 2rem;
                        max-width: 800px;
                        margin: 0 auto;
                        color: #333;
                    }
                    h1 { color: #4695EB; }
                    .card {
                        border: 1px solid #ddd;
                        border-radius: 8px;
                        padding: 1rem;
                        margin: 1rem 0;
                        background-color: #f9f9f9;
                    }
                </style>
            </head>
            <body>
                <h1>Direct HTML Test</h1>
                <div class="card">
                    <p>This is a simple HTML page without using Qute templates.</p>
                    <p>If you can see this, then basic routing is working.</p>
                </div>
                <div id="app">
                    <!-- This is where Summon would mount components -->
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}
