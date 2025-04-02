package code.yousef.application.service

import code.yousef.model.Project
import code.yousef.repository.ProjectRepo
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.persistence.repository.ProjectRepoImpl
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.response.ProjectResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
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

    suspend fun getProjectById(id: Uuid): Project? {
        return projectRepo.findProjectById(id)
    }

    suspend fun createProject(request: CreateUpdateProjectRequest): Project {
        val project = Project(
            id = "",
            title = request.title ?: "",
            description = request.description ?: "",
            imageUrl = request.imageUrl ?: "",
            modelUrl = request.modelUrl ?: "",
            technologies = request.technologies ?: emptyList(),
            githubUrl = request.githubUrl ?: "",
            demoUrl = request.demoUrl ?: "",
            createdAt = Clock.System.now().toString(),
            updatedAt = Clock.System.now().toString(),
            featured = request.featured ?: false
        )
        return projectRepo.saveProject(project)
    }

    suspend fun updateProject(id: Uuid, request: CreateUpdateProjectRequest): Project? {
        val existingProject = projectRepo.findProjectById(id) ?: return null
        
        val updatedProject = existingProject.copy(
            title = request.title ?: existingProject.title,
            description = request.description ?: existingProject.description,
            imageUrl = request.imageUrl ?: existingProject.imageUrl,
            modelUrl = request.modelUrl ?: existingProject.modelUrl,
            technologies = request.technologies ?: existingProject.technologies,
            githubUrl = request.githubUrl ?: existingProject.githubUrl,
            demoUrl = request.demoUrl ?: existingProject.demoUrl,
            featured = request.featured ?: existingProject.featured,
            updatedAt = Clock.System.now().toString()
        )
        
        return projectRepo.saveProject(updatedProject)
    }

    suspend fun deleteProject(id: Uuid): Boolean {
        return projectRepo.deleteProject(id)
    }

    suspend fun getProjectsByTechnology(technology: String): List<Project> {
        return projectRepo.findByTechnology(technology)
    }

    suspend fun toggleFeatured(id: Uuid): Project? {
        val project = projectRepo.findProjectById(id) ?: return null
        val updatedProject = project.copy(
            featured = !project.featured,
            updatedAt = Clock.System.now().toString()
        )
        return projectRepo.saveProject(updatedProject)
    }

    fun toResponse(project: Project): ProjectResponse {
        val uuid = if (project.id.isNotEmpty()) {
            try {
                Uuid.parse(project.id)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
        
        return ProjectResponse(
            id = uuid,
            title = project.title,
            description = project.description,
            imageUrl = project.imageUrl,
            modelUrl = project.modelUrl,
            technologies = project.technologies,
            githubUrl = project.githubUrl,
            demoUrl = project.demoUrl,
            featured = project.featured,
            createdAt = project.createdAt,
            updatedAt = project.updatedAt
        )
    }
}