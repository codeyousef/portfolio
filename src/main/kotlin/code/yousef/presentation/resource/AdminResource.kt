package code.yousef.presentation.resource

import TemplateService
import code.yousef.application.usecase.blog.CreateBlogUseCase
import code.yousef.application.usecase.blog.DeleteBlogUseCase
import code.yousef.application.usecase.blog.GetBlogsUseCase
import code.yousef.application.usecase.blog.UpdateBlogUseCase
import code.yousef.application.usecase.project.CreateProjectUseCase
import code.yousef.application.usecase.project.DeleteProjectUseCase
import code.yousef.application.usecase.project.GetProjectsUseCase
import code.yousef.application.usecase.project.UpdateProjectUseCase
import code.yousef.infrastructure.persistence.mapper.BlogPostMapper
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import io.quarkus.qute.TemplateInstance
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.*

@Path("/admin")
class AdminResource @Inject constructor(
    private val templateService: TemplateService,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val createProjectUseCase: CreateProjectUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val getBlogsUseCase: GetBlogsUseCase,
    private val createBlogUseCase: CreateBlogUseCase,
    private val updateBlogUseCase: UpdateBlogUseCase,
    private val deleteBlogUseCase: DeleteBlogUseCase
) {

    val projectMapper = ProjectMapper()
    val blogMapper = BlogPostMapper()

    // Dashboard
    @GET
    @Path("/dashboard")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    suspend fun getDashboard(): String? {
        return templateService.renderAdminDashboard()
    }

    // Projects list
    @GET
    @Path("/projects")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    suspend fun getProjects(): String? {
        return templateService.renderAdminProjects()
    }

    // New project form
    @GET
    @Path("/projects/new")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    suspend fun getNewProjectForm(): String? {
        return templateService.renderProjectForm()
    }

    // Create project
    @POST
    @Path("/projects")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun createProject(request: CreateUpdateProjectRequest): Response {
        val projectResponse = createProjectUseCase.execute(request)
        return Response.ok(mapOf("id" to projectResponse.id, "redirect" to "/admin/projects")).build()
    }

    // Edit project form
    @GET
    @Path("/projects/{id}/edit")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    suspend fun getEditProjectForm(@PathParam("id") id: UUID): Response {
        val project = getProjectsUseCase.getProjectById(id)
        return if (project != null) {
            Response.ok(templateService.renderProjectForm(projectMapper.toEntity(project))).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("Project not found").build()
        }
    }

    // Update project
    @PUT
    @Path("/projects/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun updateProject(@PathParam("id") id: UUID, request: CreateUpdateProjectRequest): Response {
        val projectResponse = updateProjectUseCase.execute(id, request)
        return if (projectResponse != null) {
            Response.ok(mapOf("id" to projectResponse.id, "redirect" to "/admin/projects")).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("Project not found").build()
        }
    }

    // Delete project
    @DELETE
    @Path("/projects/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun deleteProject(@PathParam("id") id: UUID): Response {
        val success = deleteProjectUseCase.execute(id)
        return if (success) {
            Response.ok(mapOf("success" to true)).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity(mapOf("success" to false)).build()
        }
    }

    // Blog posts list
    @GET
    @Path("/blog")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    suspend fun getBlogPosts(): String? {
        return templateService.renderAdminBlogPosts()
    }

    // New blog post form
    @GET
    @Path("/blog/new")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    suspend fun getNewBlogPostForm(): String? {
        return templateService.renderBlogPostForm()
    }

    // Create blog post
    @POST
    @Path("/blog")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun createBlogPost(request: CreateUpdateBlogRequest): Response {
        val blogResponse = createBlogUseCase.execute(request)
        return Response.ok(mapOf("id" to blogResponse.id, "redirect" to "/admin/blog")).build()
    }

    // Edit blog post form
    @GET
    @Path("/blog/{id}/edit")
    @Produces(MediaType.TEXT_HTML)
    @RolesAllowed("ADMIN")
    suspend fun getEditBlogPostForm(@PathParam("id") id: UUID): Response {
        val blog = getBlogsUseCase.getBlogById(id)
        return if (blog != null) {
            Response.ok(templateService.renderBlogPostForm(blogMapper.toEntityFromResponse(blog))).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("Blog post not found").build()
        }
    }

    // Update blog post
    @PUT
    @Path("/blog/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun updateBlogPost(@PathParam("id") id: UUID, request: CreateUpdateBlogRequest): Response {
        val blogResponse = updateBlogUseCase.execute(id, request)
        return if (blogResponse != null) {
            Response.ok(mapOf("id" to blogResponse.id, "redirect" to "/admin/blog")).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("Blog post not found").build()
        }
    }

    // Delete blog post
    @DELETE
    @Path("/blog/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun deleteBlogPost(@PathParam("id") id: UUID): Response {
        val success = deleteBlogUseCase.execute(id)
        return if (success) {
            Response.ok(mapOf("success" to true)).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity(mapOf("success" to false)).build()
        }
    }
}