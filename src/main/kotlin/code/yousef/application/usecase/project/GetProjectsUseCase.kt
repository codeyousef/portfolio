package code.yousef.application.usecase.project

import code.yousef.application.service.ProjectService
import code.yousef.model.Project
import code.yousef.presentation.dto.response.ProjectListResponse
import code.yousef.presentation.dto.response.ProjectResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID


@ApplicationScoped
class GetProjectsUseCase @Inject constructor(
    private val projectService: ProjectService
) {
    suspend fun getAllProjects(): List<Project> {
        return projectService.getAllProjects()
    }

    suspend fun getFeaturedProjects(): List<Project> {
        return projectService.getFeaturedProjects()
    }

    suspend fun getProjectById(id: UUID): Project? {
        return projectService.getProjectById(id)
    }

    suspend fun getProjectResponseById(id: UUID): ProjectResponse? {
        val project = projectService.getProjectById(id) ?: return null
        return projectService.toResponse(project)
    }

    suspend fun getAllProjectsResponse(): ProjectListResponse {
        val projects = projectService.getAllProjects()
        val projectResponses = projects.map { projectService.toResponse(it) }
        return ProjectListResponse(projectResponses, projects.size)
    }
}