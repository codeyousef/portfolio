package code.yousef.infrastructure.persistence.mapper

import code.yousef.model.Project
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.response.ProjectResponse
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import java.util.UUID

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class ProjectMapper {

    /**
     * Convert ProjectEntity to Project model
     */
    fun toModel(entity: ProjectEntity): Project {
        return Project(
            id = entity.id.toString(),
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            modelUrl = entity.modelUrl,
            technologies = entity.technologies,
            githubUrl = entity.githubUrl,
            demoUrl = entity.demoUrl,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
            featured = entity.featured
        )
    }

    /**
     * Convert Project model to ProjectEntity
     */
    fun toEntity(project: Project): ProjectEntity {
        val entity = ProjectEntity()
        if (project.id.isNotEmpty()) {
            entity.id = UUID.fromString(project.id)
        }
        entity.title = project.title
        entity.description = project.description
        entity.imageUrl = project.imageUrl ?: ""
        entity.modelUrl = project.modelUrl ?: ""
        entity.technologies = project.technologies
        entity.githubUrl = project.githubUrl ?: ""
        entity.demoUrl = project.demoUrl ?: ""
        entity.featured = project.featured
        entity.createdAt = project.createdAt?.let { Instant.parse(it) } ?: Clock.System.now()
        entity.updatedAt = project.updatedAt?.let { Instant.parse(it) } ?: Clock.System.now()
        return entity
    }

    /**
     * Update an existing ProjectEntity from Project model
     */
    fun updateEntity(entity: ProjectEntity, project: Project): ProjectEntity {
        entity.title = project.title
        entity.description = project.description
        entity.imageUrl = project.imageUrl ?: ""
        entity.modelUrl = project.modelUrl ?: ""
        entity.technologies = project.technologies
        entity.githubUrl = project.githubUrl ?: ""
        entity.demoUrl = project.demoUrl ?: ""
        entity.featured = project.featured
        entity.updatedAt = Clock.System.now()
        return entity
    }

    /**
     * Convert Project to ProjectResponse
     */
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

    /**
     * Convert CreateUpdateProjectRequest to Project model
     */
    fun toDomain(request: CreateUpdateProjectRequest, existingProject: Project? = null): Project {
        return Project(
            id = existingProject?.id ?: "",
            title = request.title ?: existingProject?.title ?: "",
            description = request.description ?: existingProject?.description ?: "",
            imageUrl = request.imageUrl ?: existingProject?.imageUrl ?: "",
            modelUrl = request.modelUrl ?: existingProject?.modelUrl ?: "",
            technologies = request.technologies ?: existingProject?.technologies ?: emptyList(),
            githubUrl = request.githubUrl ?: existingProject?.githubUrl ?: "",
            demoUrl = request.demoUrl ?: existingProject?.demoUrl ?: "",
            createdAt = existingProject?.createdAt ?: Clock.System.now().toString(),
            updatedAt = Clock.System.now().toString(),
            featured = request.featured ?: existingProject?.featured ?: false
        )
    }
}