package code.yousef.presentation.resource

import code.yousef.application.service.ProjectService
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.qute.ContactSectionComponent
import code.yousef.infrastructure.qute.HomePageComponent
import code.yousef.infrastructure.qute.ProjectsSectionComponent
import code.yousef.infrastructure.qute.QuteComponents
import code.yousef.infrastructure.qute.SkillsSectionComponent
import code.yousef.infrastructure.summon.SummonRenderer
import code.yousef.summon.components.display.Text
import code.yousef.summon.components.layout.Box
import code.yousef.summon.modifier.Modifier
import code.yousef.ui.AppRoot
import code.yousef.ui.HomePage
import io.quarkus.qute.Template
import io.quarkus.qute.Location
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/portfolio")
class PortfolioResource @Inject constructor(
    private val projectService: ProjectService,
    private val projectMapper: ProjectMapper,
    private val summonRenderer: SummonRenderer,
    private val quteComponents: QuteComponents,
    @Location("index.html")
    private val indexTemplate: Template
) {

    @GET
    @Produces(MediaType.TEXT_HTML)
    suspend fun getPortfolioPage(): String {
        println("PortfolioResource.getPortfolioPage() called - rendering portfolio page with Qute templates")
        println("QuteComponents instance: ${quteComponents.javaClass.name}")
        println("IndexTemplate instance: ${indexTemplate.javaClass.name}")
        println("ProjectService instance: ${projectService.javaClass.name}")
        println("ProjectMapper instance: ${projectMapper.javaClass.name}")

        try {
            println("Rendering index.html template with Qute")

            // Get featured projects for the projects section
            val projects = projectService.getFeaturedProjects()
            println("Retrieved ${projects.size} featured projects")

            // Convert projects to entities for the template
            val projectEntities = projects.map { project -> projectMapper.toEntity(project) }
            println("Converted ${projectEntities.size} projects to entities")

            // Render the index.html template with the data
            val html = indexTemplate.data(
                "title", "Yousef's Portfolio - Modern Software Development",
                "description", "Showcasing software development projects and services specializing in Kotlin, Quarkus, and modern web technologies.",
                "projects", projectEntities
            ).render()

            println("PortfolioResource.getPortfolioPage() completed successfully, result length: ${html.length}")
            println("First 100 chars of HTML: ${html.take(100)}")
            return html
        } catch (e: Exception) {
            println("Error rendering portfolio page with Qute: ${e}")
            println("Exception type: ${e.javaClass.name}")
            println("Exception message: ${e.message}")
            println("Stack trace: ${e.stackTraceToString()}")
            return "<h1>Error rendering portfolio page</h1><p>${e.message}</p><pre>${e.stackTraceToString()}</pre>"
        }
    }

    @GET
    @Path("/api/projects")
    @Produces(MediaType.TEXT_HTML)
    suspend fun getProjectsSection(): String {
        println("PortfolioResource.getProjectsSection() called - rendering projects section")

        try {
            println("Fetching featured projects from ProjectService")
            val projects = projectService.getFeaturedProjects()
            println("Retrieved ${projects.size} featured projects")

            println("Converting projects to entities")
            val projectEntities = projects.map { project -> projectMapper.toEntity(project) }
            println("Converted ${projectEntities.size} projects to entities")

            println("Rendering projects section with Qute template")
            val html = quteComponents.renderProjectsSectionComponent(projectEntities)

            println("PortfolioResource.getProjectsSection() completed successfully, result length: ${html.length}")
            println("First 100 chars of HTML: ${html.take(100)}")
            return html
        } catch (e: Exception) {
            println("Error rendering projects section with Qute: ${e}")
            println("Exception type: ${e.javaClass.name}")
            println("Exception message: ${e.message}")
            println("Stack trace: ${e.stackTraceToString()}")
            return "<div>Error rendering projects section: ${e.message}<pre>${e.stackTraceToString()}</pre></div>"
        }
    }
}
