package code.yousef.presentation.resource

import code.yousef.application.service.ProjectService
import code.yousef.application.usecase.project.GetProjectsUseCase
import code.yousef.presentation.dto.response.ProjectListResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/api/projects")
@ApplicationScoped
class ProjectResource @Inject constructor(
    private val getProjectsUseCase: GetProjectsUseCase,
    private val projectService: ProjectService
) {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getProjects(): ProjectListResponse {
        return getProjectsUseCase.getAllProjectsResponse()
    }
    
    @GET
    @Path("/featured")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFeaturedProjects(): ProjectListResponse {
        val projects = getProjectsUseCase.getFeaturedProjects()
        val projectResponses = projects.map { projectService.toResponse(it) }
        return ProjectListResponse(projectResponses, projects.size)
    }
} 