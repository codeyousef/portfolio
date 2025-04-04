package code.yousef.presentation.resource

import code.yousef.application.service.ProjectService
import code.yousef.application.service.ServiceService
import code.yousef.application.service.BlogService
import code.yousef.model.Project
import code.yousef.model.BlogPost
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import kotlin.uuid.ExperimentalUuidApi

/**
 * This resource provides APIs that return data using the shared models,
 * which can be consumed by the Kotlin/JS frontend
 */
@OptIn(ExperimentalUuidApi::class)
@Path("/api/v1")
@ApplicationScoped
class ApiResource @Inject constructor(
    private val projectService: ProjectService,
    private val serviceService: ServiceService,
    private val blogService: BlogService
) {
    
    @GET
    @Path("/projects")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getProjects(): List<Project> {
        // Get projects from the database using the existing service
        val domainProjects = projectService.getAllProjects()
        
        // Convert domain models to shared models
        return domainProjects.map { project ->
            Project(
                id = project.id.toString(),
                title = project.title,
                description = project.description,
                imageUrl = project.imageUrl,
                modelUrl = project.modelUrl,
                technologies = project.technologies,
                githubUrl = project.githubUrl,
                demoUrl = project.demoUrl,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt,
                featured = project.featured
            )
        }
    }
    
    @GET
    @Path("/projects/featured")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFeaturedProjects(): List<Project> {
        // Get featured projects from the database
        val domainProjects = projectService.getFeaturedProjects()
        
        // Convert domain models to shared models
        return domainProjects.map { project ->
            Project(
                id = project.id.toString(),
                title = project.title,
                description = project.description,
                imageUrl = project.imageUrl,
                modelUrl = project.modelUrl,
                technologies = project.technologies,
                githubUrl = project.githubUrl,
                demoUrl = project.demoUrl,
                createdAt = project.createdAt,
                updatedAt = project.updatedAt,
                featured = project.featured
            )
        }
    }
    
    @GET
    @Path("/services")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getServices(): List<code.yousef.model.Service> {
        // Get services directly as shared models
        return serviceService.getAllServices()
    }
    
    @GET
    @Path("/blog")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getBlogPosts(): List<BlogPost> {
        // Get blog posts directly as shared models
        return blogService.getAllBlogs()
    }
} 