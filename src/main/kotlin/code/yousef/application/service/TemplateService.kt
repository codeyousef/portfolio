import code.yousef.application.service.BlogService
import code.yousef.application.service.ProjectService
import code.yousef.domain.model.Project
import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import code.yousef.infrastructure.persistence.mapper.BlogPostMapper
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.template.AdminTemplates
import code.yousef.infrastructure.template.BlogTemplates
import code.yousef.infrastructure.template.PortfolioTemplates
import io.quarkus.qute.TemplateInstance
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class TemplateService @Inject constructor(
    var projectService: ProjectService,
    var blogService: BlogService,
    var portfolioTemplates: PortfolioTemplates,
    var blogTemplates: BlogTemplates,
    var adminTemplates: AdminTemplates,
    val projectMapper: ProjectMapper,
    val blogPostMapper: BlogPostMapper
) {

    // Existing portfolio and blog methods remain unchanged
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
        
        // Calculate total pages - if you have a way to get total count
        // This is an approximation - modify as needed based on your pagination implementation
        val totalPages = (blogPosts.size + size - 1) / size // If blogPosts.size is the total count
        
        return blogTemplates.buildBlogPage(blogResponse, page, size, totalPages)
    }

    suspend fun renderBlogPostPage(slug: String): TemplateInstance {
        val post = blogService.getBlogBySlug(slug)
        val postBlogResponse = post?.let { blogPostMapper.toResponse(it) }

        return if (post != null) {
            blogTemplates.buildBlogPostPage(postBlogResponse)
        } else {
            blogTemplates.buildNotFoundPage()
        }
    }

    // Update the admin methods to use the injected adminTemplates service
    // Also change return types from StringBuilder to TemplateInstance
    fun renderLoginPage(): String? {
        return adminTemplates.buildLoginPage()
    }

    fun renderAdminDashboard(): String? {
        return adminTemplates.buildDashboard()
    }

    suspend fun renderAdminProjects(): String? {
        val projects = projectService.getAllProjects()
        val projectEntities = projects.map { project: Project -> projectMapper.toEntity(project) }
        return adminTemplates.buildProjectsPage(projectEntities)
    }

    suspend fun renderAdminBlogPosts(): String? {
        val blogPosts = blogService.getAllBlogs()
        val blogPostEntities = blogPosts.map { blogPost -> blogPostMapper.toEntity(blogPost) }
        return adminTemplates.buildBlogPostsPage(blogPostEntities)
    }

    fun renderProjectForm(projectEntity: ProjectEntity? = null): String? {
        return adminTemplates.buildProjectForm(projectEntity)
    }

    fun renderBlogPostForm(blogPostEntity: BlogPostEntity? = null): String? {
        return adminTemplates.buildBlogPostForm(blogPostEntity)
    }
}