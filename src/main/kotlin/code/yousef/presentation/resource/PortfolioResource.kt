package code.yousef.presentation.resource

import code.yousef.application.service.ProjectService
import code.yousef.infrastructure.template.PortfolioTemplates
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/")
class PortfolioResource {

    @Inject
    lateinit var portfolioTemplates: PortfolioTemplates

    @Inject
    lateinit var projectService: ProjectService

    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getHomePage(): Uni<String>?
    {
        return projectService.getAllProjects()
            .onItem().transform { projects ->
                portfolioTemplates.buildHomePage(projects).render()
            }
    }

    @GET
    @Path("/api/projects")
    @Produces(MediaType.TEXT_HTML)
    fun getProjectsSection(): Uni<String> {
        return projectService.getAllProjects()
            .onItem().transform { projects ->
                portfolioTemplates.buildProjectsSection(projects)
            }
    }
}
