package code.yousef.infrastructure.persistence.mapper

import code.yousef.domain.model.Project
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.response.ProjectResponse
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ApplicationScoped
class ProjectMapper {
    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    fun toDomain(entity: ProjectEntity): Project {
        return Project(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
            modelUrl = entity.modelUrl,
            technologies = entity.technologies,
            githubUrl = entity.githubUrl,
            demoUrl = entity.demoUrl,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            featured = entity.featured
        )
    }

    fun toEntity(project: Project): ProjectEntity {
        val entity = ProjectEntity()
        entity.id = project.id
        entity.title = project.title
        entity.description = project.description
        entity.imageUrl = project.imageUrl
        entity.modelUrl = project.modelUrl
        entity.technologies = project.technologies
        entity.githubUrl = project.githubUrl
        entity.demoUrl = project.demoUrl
        entity.createdAt = project.createdAt
        entity.updatedAt = project.updatedAt
        entity.featured = project.featured
        return entity
    }

    fun updateEntity(entity: ProjectEntity, project: Project): ProjectEntity {
        entity.id = project.id
        entity.title = project.title
        entity.description = project.description
        entity.imageUrl = project.imageUrl
        entity.modelUrl = project.modelUrl
        entity.technologies = project.technologies
        entity.githubUrl = project.githubUrl
        entity.demoUrl = project.demoUrl
        entity.updatedAt = LocalDateTime.now()
        entity.featured = project.featured
        return entity
    }

    fun toDomain(request: CreateUpdateProjectRequest, existingProject: Project? = null): Project {
        val now = LocalDateTime.now()
        return Project(
            id = existingProject?.id,
            title = request.title ?: existingProject?.title ?: "",
            description = request.description ?: existingProject?.description ?: "",
            imageUrl = request.imageUrl ?: existingProject?.imageUrl ?: "",
            modelUrl = request.modelUrl ?: existingProject?.modelUrl ?: "",
            technologies = request.technologies ?: existingProject?.technologies ?: emptyList(),
            githubUrl = request.githubUrl ?: existingProject?.githubUrl ?: "",
            demoUrl = request.demoUrl ?: existingProject?.demoUrl ?: "",
            createdAt = existingProject?.createdAt ?: now,
            updatedAt = now,
            featured = request.featured ?: existingProject?.featured ?: false
        )
    }

    fun toResponse(project: Project): ProjectResponse {
        return ProjectResponse(
            id = project.id ?: -1,
            title = project.title,
            description = project.description,
            imageUrl = project.imageUrl,
            modelUrl = project.modelUrl,
            technologies = project.technologies,
            githubUrl = project.githubUrl,
            demoUrl = project.demoUrl,
            featured = project.featured,
            createdAt = project.createdAt.format(dateFormatter),
            updatedAt = project.updatedAt.format(dateFormatter)
        )
    }
}