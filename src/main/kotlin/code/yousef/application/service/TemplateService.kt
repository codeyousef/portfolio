package code.yousef.application.service

import code.yousef.domain.model.Project
import code.yousef.infrastructure.persistence.entity.BlogPost
import code.yousef.infrastructure.persistence.entity.ProjectEntity
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
    val projectMapper: ProjectMapper
) {

    suspend fun renderHomePage(): TemplateInstance {
        val projects = projectService.getFeaturedProjects()
        val projectEntities = projects.map { project: Project -> projectMapper.toEntity(project) }

        return portfolioTemplates.buildHomePage(projectEntities)
    }

    fun renderProjectsSection(projectEntities: List<ProjectEntity>): String {
        return portfolioTemplates.buildProjectsSection(projectEntities)
    }

    fun renderBlogPage(page: Int = 0, size: Int = 9): Uni<TemplateInstance> {
        return blogService.getPublishedBlogPosts(page, size)
            .onItem().transform { posts ->
                blogTemplates.buildBlogPage(posts, page, size)
            }
    }

    fun renderBlogPostPage(slug: String): Uni<TemplateInstance> {
        return blogService.getBlogPostBySlug(slug)
            .onItem().transform { post ->
                if (post != null) {
                    blogTemplates.buildBlogPostPage(post)
                } else {
                    // Handle not found case - could return a 404 template
                    blogTemplates.buildNotFoundPage()
                }
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

    fun renderAdminBlogPosts(): Uni<String> {
        return blogService.getAllBlogPosts()
            .onItem().transform { posts ->
                AdminTemplates.buildBlogPostsPage(posts).toString()
            }
    }

    fun renderProjectForm(projectEntity: ProjectEntity? = null): StringBuilder {
        return AdminTemplates.buildProjectForm(projectEntity)
    }

    fun renderBlogPostForm(blogPost: BlogPost? = null): StringBuilder {
        return AdminTemplates.buildBlogPostForm(blogPost)
    }
}