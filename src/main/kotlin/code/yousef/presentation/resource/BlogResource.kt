package code.yousef.presentation.resource

import code.yousef.application.service.BlogService
import code.yousef.infrastructure.template.BlogTemplates
import io.quarkus.qute.TemplateInstance
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType

@Path("/blog")
class BlogResource {

    @Inject
    lateinit var blogTemplates: BlogTemplates

    @Inject
    lateinit var blogService: BlogService

    @GET
    @Produces(MediaType.TEXT_HTML)
    fun getBlogPage(@QueryParam("page") @DefaultValue("1") page: Int): TemplateInstance {
        val pageSize = 10
        val blogPosts = blogService.getPublishedBlogPosts(page, pageSize)
            .await().indefinitely()
            .sortedByDescending { it.createdAt }
        return blogTemplates.buildBlogPage(blogPosts, page, pageSize)
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    fun getBlogPostPage(@PathParam("id") id: Long): TemplateInstance {
        val blogPost = blogService.getBlogPostById(id).await().indefinitely()
        return blogTemplates.buildBlogPostPage(blogPost)
    }
}
