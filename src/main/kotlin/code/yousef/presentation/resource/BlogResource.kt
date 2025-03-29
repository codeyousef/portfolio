package code.yousef.presentation.resource

import code.yousef.application.usecase.blog.CreateBlogUseCase
import code.yousef.application.usecase.blog.DeleteBlogUseCase
import code.yousef.application.usecase.blog.GetBlogsUseCase
import code.yousef.application.usecase.blog.UpdateBlogUseCase
import code.yousef.infrastructure.template.BlogTemplates
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.response.BlogListResponse
import io.quarkus.qute.TemplateInstance
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI
import java.util.*

@Path("/blog")
class BlogResource {
    @Inject
    lateinit var blogTemplates: BlogTemplates

    @Inject
    lateinit var getBlogsUseCase: GetBlogsUseCase

    @Inject
    lateinit var createBlogUseCase: CreateBlogUseCase

    @Inject
    lateinit var updateBlogUseCase: UpdateBlogUseCase

    @Inject
    lateinit var deleteBlogUseCase: DeleteBlogUseCase

    // Public endpoints
    @GET
    @Produces(MediaType.TEXT_HTML)
    suspend fun getBlogPage(
        @QueryParam("page") @DefaultValue("0") page: Int,
        @QueryParam("size") @DefaultValue("10") size: Int
    ): String? {
        val blogs = getBlogsUseCase.getPublishedBlogs(page, size).blogs
        return blogTemplates.buildBlogPage(blogs, page, size).render()
    }

    @GET
    @Path("/{slug}")
    @Produces(MediaType.TEXT_HTML)
    suspend fun getBlogPostBySlug(@PathParam("slug") slug: String): String? {
        val blog = getBlogsUseCase.getBlogBySlug(slug)
        return blogTemplates.buildBlogPostPage(blog).render()
    }

    // Admin API endpoints
    @GET
    @Path("/api/blogs")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun getAllBlogs(): BlogListResponse {
        return getBlogsUseCase.getAllBlogs()
    }

    @GET
    @Path("/api/blogs/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun getBlogById(@PathParam("id") id: UUID): Response {
        val blogResponse = getBlogsUseCase.getBlogById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(blogResponse).build()
    }

    @POST
    @Path("/api/blogs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun createBlog(request: CreateUpdateBlogRequest): Response {
        val blogResponse = createBlogUseCase.execute(request)
        return Response.created(URI.create("/api/blogs/${blogResponse.id}"))
            .entity(blogResponse)
            .build()
    }

    @PUT
    @Path("/api/blogs/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun updateBlog(
        @PathParam("id") id: UUID,
        request: CreateUpdateBlogRequest
    ): Response {
        val blogResponse = updateBlogUseCase.execute(id, request)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(blogResponse).build()
    }

    @PUT
    @Path("/api/blogs/{id}/toggle-published")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun togglePublished(@PathParam("id") id: UUID): Response {
        val blogResponse = updateBlogUseCase.togglePublished(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(blogResponse).build()
    }

    @DELETE
    @Path("/api/blogs/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun deleteBlog(@PathParam("id") id: UUID): Response {
        val deleted = deleteBlogUseCase.execute(id)
        return if (deleted) Response.noContent().build()
        else Response.status(Response.Status.NOT_FOUND).build()
    }
}