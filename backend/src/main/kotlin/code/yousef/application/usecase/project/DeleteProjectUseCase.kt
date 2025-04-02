package code.yousef.application.usecase.project

import code.yousef.application.service.ProjectService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class DeleteProjectUseCase @Inject constructor(
    private val projectService: ProjectService
) {
    suspend fun execute(id: Uuid): Boolean {
        return projectService.deleteProject(id)
    }
}