package code.yousef.application.service

import code.yousef.domain.model.Project
import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import code.yousef.infrastructure.persistence.mapper.BlogPostMapper
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.template.AdminTemplates
import code.yousef.infrastructure.template.BlogTemplates
import code.yousef.infrastructure.template.PortfolioTemplates
import io.quarkus.qute.TemplateInstance
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class TemplateService @Inject constructor(
    var projectService: ProjectService,
    var blogService: BlogService,
    var portfolioTemplates: PortfolioTemplates,
    var blogTemplates: BlogTemplates,
    val projectMapper: ProjectMapper,
    val blogPostMapper: BlogPostMapper
) {

    suspend fun renderHomePage(): TemplateInstance {
        val projects = projectService.getFeaturedProjects()
        val projectEntities = projects.map { project: Project -> projectMapper.toEntity(project) }

        return portfolioTemplates.buildHomePage(projectEntities)
    }

    fun renderProjectsSection(projectEntities: List<ProjectEntity>): String {
        return portfolioTemplates.buildProjectsSection(projectEntities)
    }

    suspend fun renderBlogPage(page: Int = 0, size: Int = 9): TemplateInstance {
        val blogPosts = blogService.getPublishedBlogs(page, size)
        val blogResponse = blogPosts.map { blogPost -> blogPostMapper.toResponse(blogPost) }

        return blogTemplates.buildBlogPage(blogResponse, page, size)

    }

    suspend fun renderBlogPostPage(slug: String): TemplateInstance {
        val post = blogService.getBlogBySlug(slug)
        val postBlogResponse = post?.let { blogPostMapper.toResponse(it) }

        return if (post != null) {
            blogTemplates.buildBlogPostPage(postBlogResponse)
        } else {
            // Handle not found case - could return a 404 template
            blogTemplates.buildNotFoundPage()
        }

    }

    // Admin pages - keeping original implementation for now
    fun renderLoginPage(): StringBuilder {
        return AdminTemplates.buildLoginPage()
    }

    fun renderAdminDashboard(): StringBuilder {
        return AdminTemplates.buildDashboard()
    }

    suspend fun renderAdminProjects(): StringBuilder {
        val projects = projectService.getAllProjects()
        val projectEntities = projects.map { project: Project -> projectMapper.toEntity(project) }
        return AdminTemplates.buildProjectsPage(projectEntities)

    }

    suspend fun renderAdminBlogPosts(): StringBuilder {
        val blogPosts = blogService.getAllBlogs()
        val blogPostEntities = blogPosts.map { blogPost -> blogPostMapper.toEntity(blogPost) }

        return AdminTemplates.buildBlogPostsPage(blogPostEntities)

    }

    fun renderProjectForm(projectEntity: ProjectEntity? = null): StringBuilder {
        return AdminTemplates.buildProjectForm(projectEntity)
    }

    fun renderBlogPostForm(blogPostEntity: BlogPostEntity? = null): StringBuilder {
        return AdminTemplates.buildBlogPostForm(blogPostEntity)
    }
}