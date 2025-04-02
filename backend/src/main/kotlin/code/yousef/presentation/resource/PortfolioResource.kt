package code.yousef.presentation.resource

import code.yousef.application.service.ProjectService
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.template.PortfolioTemplates
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/")
class PortfolioResource @Inject constructor(
    private val portfolioTemplates: PortfolioTemplates,
    var projectService: ProjectService,
    val projectMapper: ProjectMapper
) {

    @GET
    @Produces(MediaType.TEXT_HTML)
    suspend fun getHomePage(): String? {
        val projects = projectService.getAllProjects()
        val projectEntities = projects.map { project -> projectMapper.toEntity(project) }

        return portfolioTemplates.buildHomePage(projectEntities).render()

    }

    @GET
    @Path("/api/projects")
    @Produces(MediaType.TEXT_HTML)
    suspend fun getProjectsSection(): String {
        val projects = projectService.getFeaturedProjects()
        val projectEntities = projects.map { project -> projectMapper.toEntity(project) }
        return portfolioTemplates.buildProjectsSection(projectEntities)
    }
}
