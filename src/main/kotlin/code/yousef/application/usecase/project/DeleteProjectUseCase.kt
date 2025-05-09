package code.yousef.application.usecase.project

import code.yousef.application.service.ProjectService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class DeleteProjectUseCase @Inject constructor(
    private val projectService: ProjectService
) {
    suspend fun execute(id: UUID): Boolean {
        return projectService.deleteProject(id)
    }
}