package code.yousef.resource

import code.yousef.service.ProjectService
import code.yousef.template.PortfolioTemplates
import io.quarkus.qute.TemplateInstance
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
        return projectService.getFeaturedProjects()
            .onItem().transform { projects ->
                portfolioTemplates.buildHomePage(projects).render()
            }
    }

    @GET
    @Path("/api/projects")
    @Produces(MediaType.TEXT_HTML)
    fun getProjectsSection(): Uni<String> {
        return projectService.getFeaturedProjects()
            .onItem().transform { projects ->
                portfolioTemplates.buildProjectsSection(projects)
            }
    }
}
