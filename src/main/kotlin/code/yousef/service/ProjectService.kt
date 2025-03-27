package code.yousef.service

import code.yousef.model.Project
import code.yousef.repository.ProjectRepository
import io.smallrye.mutiny.Uni
import java.time.LocalDateTime
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class ProjectService {
    
    @Inject
    lateinit var projectRepository: ProjectRepository
    
    fun getAllProjects(): Uni<List<Project>> {
        return projectRepository.listAll()
    }
    
    fun getFeaturedProjects(): Uni<List<Project>> {
        return projectRepository.findFeaturedProjects()
    }
    
    fun getProjectById(id: Long): Uni<Project> {
        return projectRepository.findById(id)
    }
    
    fun createProject(project: Project): Uni<Project> {
        return projectRepository.saveProject(project)
    }
    
    fun updateProject(id: Long, updatedProject: Project): Uni<Project> {
        return projectRepository.findById(id)
            .onItem().ifNotNull().transformToUni { existingProject ->
                existingProject.title = updatedProject.title
                existingProject.description = updatedProject.description
                existingProject.imageUrl = updatedProject.imageUrl
                existingProject.modelUrl = updatedProject.modelUrl
                existingProject.technologies = updatedProject.technologies
                existingProject.githubUrl = updatedProject.githubUrl
                existingProject.demoUrl = updatedProject.demoUrl
                existingProject.featured = updatedProject.featured
                
                projectRepository.saveProject(existingProject)
            }
    }
    
    fun deleteProject(id: Long): Uni<Boolean> {
        return projectRepository.deleteById(id)
    }
}
