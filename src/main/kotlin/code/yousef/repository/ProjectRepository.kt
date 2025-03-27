package code.yousef.repository

import code.yousef.model.Project
import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.time.LocalDateTime

@ApplicationScoped
class ProjectRepository(private val sessionFactory: SessionFactory) : PanacheRepository<Project> {

    fun findFeaturedProjects(): Uni<List<Project>> {
        return sessionFactory.withSession { list("featured = true ORDER BY createdAt DESC") }
    }

    fun saveProject(project: Project): Uni<Project> {
        val now = LocalDateTime.now()

        if (project.id == null) {
            project.createdAt = now
        }

        project.updatedAt = now

        return sessionFactory.withSession { persistAndFlush(project) }
    }
}
