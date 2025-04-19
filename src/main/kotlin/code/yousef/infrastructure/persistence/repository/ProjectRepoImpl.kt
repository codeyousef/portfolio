package code.yousef.infrastructure.persistence.repository

import code.yousef.model.Project
import code.yousef.repository.ProjectRepo
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.util.UUID

@ApplicationScoped
class ProjectRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val projectMapper: ProjectMapper
) : PanacheRepositoryBase<ProjectEntity, UUID>, ProjectRepo {



    override suspend fun findProjectById(id: UUID): Project? {
        val project = sessionFactory.withSession {
            findById(id)
        }.awaitSuspending()
        return project?.let { projectMapper.toModel(it) }
    }

    override suspend fun findFeaturedProjects(): List<Project> {
        val projects = sessionFactory.withSession {
            find("featured = true ORDER BY createdAt DESC").list()
        }.awaitSuspending()
        return projects.map { project -> projectMapper.toModel(project) }
    }

    override suspend fun saveProject(project: Project): Project {
        val entity = if (project.id.isNotEmpty()) {
            val existingEntity = sessionFactory.withSession {
                findById(UUID.fromString(project.id))
            }.awaitSuspending()
            
            if (existingEntity != null) {
                projectMapper.updateEntity(existingEntity, project)
            } else {
                projectMapper.toEntity(project)
            }
        } else {
            projectMapper.toEntity(project)
        }

        val savedProject = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(entity)
            }
        }.awaitSuspending()

        return projectMapper.toModel(savedProject)
    }

    override suspend fun getAllProjects(): List<Project> {
        val projects = sessionFactory.withSession {
            findAll().list()
        }.awaitSuspending()
        return projects.map { project -> projectMapper.toModel(project) }
    }

    override suspend fun deleteProject(id: UUID): Boolean {
        return sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id)
            }
        }.awaitSuspending()
    }

    override suspend fun findByTechnology(technology: String): List<Project> {
        val projects = sessionFactory.withSession {
            find("?1 MEMBER OF technologies", technology).list()
        }.awaitSuspending()
        return projects.map { project -> projectMapper.toModel(project) }
    }
}