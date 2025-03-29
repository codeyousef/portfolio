package code.yousef.application.service

import code.yousef.infrastructure.persistence.entity.Project
import code.yousef.domain.repository.ProjectRepo
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory

@ApplicationScoped
class ProjectService @Inject constructor(
    var projectRepo: ProjectRepo,
    private val sessionFactory: SessionFactory
) {


    fun getAllProjects(): Uni<List<Project>> {
        return sessionFactory.withSession { projectRepo.listAll() }
    }

    fun getFeaturedProjects(): Uni<List<Project>> {
        return sessionFactory.withSession { projectRepo.findFeaturedProjects() }
    }

    fun getProjectById(id: Long): Uni<Project> {
        return sessionFactory.withSession { projectRepo.findById(id) }
    }

    fun createProject(project: Project): Uni<Project> {
        return sessionFactory.withSession { projectRepo.saveProject(project) }
    }

    fun updateProject(id: Long, updatedProject: Project): Uni<Project> {
        return sessionFactory.withSession { projectRepo.findById(id) }
            .onItem().ifNotNull().transformToUni { existingProject ->
                existingProject.title = updatedProject.title
                existingProject.description = updatedProject.description
                existingProject.imageUrl = updatedProject.imageUrl
                existingProject.modelUrl = updatedProject.modelUrl
                existingProject.technologies = updatedProject.technologies
                existingProject.githubUrl = updatedProject.githubUrl
                existingProject.demoUrl = updatedProject.demoUrl
                existingProject.featured = updatedProject.featured

                projectRepo.saveProject(existingProject)
            }
    }

    fun deleteProject(id: Long): Uni<Boolean> {
        return sessionFactory.withSession { projectRepo.deleteById(id) }
    }
}
