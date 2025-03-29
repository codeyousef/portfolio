package code.yousef.application.usecase.project

import code.yousef.application.service.ProjectService
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.response.ProjectResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class CreateProjectUseCase @Inject constructor(
    private val projectService: ProjectService
) {
    suspend fun execute(request: CreateUpdateProjectRequest): ProjectResponse {
        val project = projectService.createProject(request)
        return projectService.toResponse(project)
    }
}