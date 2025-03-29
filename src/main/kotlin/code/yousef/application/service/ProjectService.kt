package code.yousef.application.service

import code.yousef.domain.model.Project
import code.yousef.domain.repository.ProjectRepo
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.persistence.repository.ProjectRepoImpl
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.response.ProjectResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import java.time.LocalDateTime
import java.util.UUID

@ApplicationScoped
class ProjectService @Inject constructor(
    private val projectRepo: ProjectRepoImpl,
    private val projectMapper: ProjectMapper,
    private val sessionFactory: SessionFactory
) {
    suspend fun getAllProjects(): List<Project> {
        return projectRepo.getAllProjects()
    }

    suspend fun getFeaturedProjects(): List<Project> {
        return projectRepo.findFeaturedProjects()
    }

    suspend fun getProjectById(id: UUID): Project? {
        return projectRepo.findProjectById(id)
    }

    suspend fun createProject(request: CreateUpdateProjectRequest): Project {
        val project = projectMapper.toDomain(request)
        return projectRepo.saveProject(project)
    }

    suspend fun updateProject(id: UUID, request: CreateUpdateProjectRequest): Project? {
        val existingProject = projectRepo.findProjectById(id) ?: return null
        val updatedProject = projectMapper.toDomain(request, existingProject)
        return projectRepo.saveProject(updatedProject)
    }

    suspend fun deleteProject(id: UUID): Boolean {
        return projectRepo.deleteProject(id)
    }

    suspend fun getProjectsByTechnology(technology: String): List<Project> {
        return projectRepo.findByTechnology(technology)
    }

    suspend fun toggleFeatured(id: UUID): Project? {
        val project = projectRepo.findProjectById(id) ?: return null
        val updatedProject = project.withUpdatedFields(featured = !project.featured)
        return projectRepo.saveProject(updatedProject)
    }

    fun toResponse(project: Project): ProjectResponse {
        return projectMapper.toResponse(project)
    }
}