package code.yousef.application.usecase.project

import code.yousef.application.service.ProjectService
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.response.ProjectResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class UpdateProjectUseCase @Inject constructor(
    private val projectService: ProjectService
) {
    suspend fun execute(id: UUID, request: CreateUpdateProjectRequest): ProjectResponse? {
        val project = projectService.updateProject(id, request) ?: return null
        return projectService.toResponse(project)
    }

    suspend fun toggleFeatured(id: UUID): ProjectResponse? {
        val project = projectService.toggleFeatured(id) ?: return null
        return projectService.toResponse(project)
    }
}