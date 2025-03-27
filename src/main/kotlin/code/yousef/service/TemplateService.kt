package code.yousef.service

import code.yousef.model.BlogPost
import code.yousef.model.Project
import code.yousef.template.AdminTemplates
import code.yousef.template.BlogTemplates
import code.yousef.template.PortfolioTemplates
import io.quarkus.qute.TemplateInstance
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class TemplateService {

    @Inject
    lateinit var projectService: ProjectService

    @Inject
    lateinit var blogService: BlogService

    @Inject
    lateinit var portfolioTemplates: PortfolioTemplates

    @Inject
    lateinit var blogTemplates: BlogTemplates

    fun renderHomePage(): Uni<TemplateInstance> {
        return projectService.getFeaturedProjects()
            .onItem().transform { projects ->
                portfolioTemplates.buildHomePage(projects)
            }
    }

    fun renderProjectsSection(projects: List<Project>): String {
        return portfolioTemplates.buildProjectsSection(projects)
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

    fun renderAdminProjects(): Uni<String> {
        return projectService.getAllProjects()
            .onItem().transform { projects ->
                AdminTemplates.buildProjectsPage(projects).toString()
            }
    }

    fun renderAdminBlogPosts(): Uni<String> {
        return blogService.getAllBlogPosts()
            .onItem().transform { posts ->
                AdminTemplates.buildBlogPostsPage(posts).toString()
            }
    }

    fun renderProjectForm(project: Project? = null): StringBuilder {
        return AdminTemplates.buildProjectForm(project)
    }

    fun renderBlogPostForm(blogPost: BlogPost? = null): StringBuilder {
        return AdminTemplates.buildBlogPostForm(blogPost)
    }
}