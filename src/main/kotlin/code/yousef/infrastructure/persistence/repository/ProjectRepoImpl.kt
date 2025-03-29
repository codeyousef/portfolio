package code.yousef.infrastructure.persistence.repository

import code.yousef.domain.model.Project
import code.yousef.domain.repository.ProjectRepo
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.util.*

@ApplicationScoped
class ProjectRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val projectMapper: ProjectMapper
) : PanacheRepositoryBase<ProjectEntity, UUID>, ProjectRepo {

    override suspend fun findProjectById(id: UUID): Project? {
        val project = sessionFactory.withSession {
            findById(id)
        }.awaitSuspending()
        return projectMapper.toDomain(project)
    }

    override suspend fun findFeaturedProjects(): List<Project> {
        val projects = sessionFactory.withSession {
            find("featured = true ORDER BY createdAt DESC").list()
        }.awaitSuspending()
        return projects.map { project -> projectMapper.toDomain(project) }
    }

    override suspend fun saveProject(project: Project): Project {
        val entity = if (project.id != null) {
            val existingEntity = sessionFactory.withSession {
                findById(project.id)
            }.awaitSuspending()
            projectMapper.updateEntity(existingEntity, project)
        } else {
            projectMapper.toEntity(project)
        }

        val savedProject = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(entity)
            }
        }.awaitSuspending()
        return projectMapper.toDomain(savedProject)
    }

    override suspend fun getAllProjects(): List<Project> {
        val projects = sessionFactory.withSession {
            findAll().list()
        }.awaitSuspending()
        return projects.map { projectMapper.toDomain(it) }
    }

    override suspend fun deleteProject(id: UUID): Boolean {
        sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id)
            }
        }.awaitSuspending()
        return true
    }

    override suspend fun findByTechnology(technology: String): List<Project> {
        val projects = sessionFactory.withSession {
            find("?1 MEMBER OF technologies", technology).list()
        }.awaitSuspending()
        return projects.map { projectMapper.toDomain(it) }
    }
}