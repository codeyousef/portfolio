//package code.yousef.resource
//
//import code.yousef.model.BlogPost
//import code.yousef.model.Project
//import code.yousef.service.BlogService
//import code.yousef.service.ProjectService
//import code.yousef.service.TemplateService
//import io.smallrye.mutiny.Uni
//import jakarta.annotation.security.RolesAllowed
//import jakarta.inject.Inject
//import jakarta.ws.rs.*
//import jakarta.ws.rs.core.MediaType
//import jakarta.ws.rs.core.Response
//
//@Path("/admin")
//class AdminResource {
//
//    @Inject
//    lateinit var templateService: TemplateService
//
//    @Inject
//    lateinit var projectService: ProjectService
//
//    @Inject
//    lateinit var blogService: BlogService
//
//    @Inject
//    lateinit var authService: AuthenticationService
//
//    // Login page
//    @GET
//    @Path("/login")
//    @Produces(MediaType.TEXT_HTML)
//    fun getLoginPage(): Response {
//        return Response.ok(templateService.renderLoginPage()).build()
//    }
//
//    // Login action
//    @POST
//    @Path("/login")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    fun login(
//        @FormParam("username") username: String,
//        @FormParam("password") password: String
//    ): Uni<Response> {
//        return authService.login(username, password)
//            .onItem().transform { loginResponse ->
//                Response.ok()
//                    .header("Authorization", "Bearer " + loginResponse.token)
//                    .entity(mapOf("redirect" to "/admin/dashboard"))
//                    .build()
//            }
//            .onFailure().recoverWithItem { e ->
//                Response.status(Response.Status.UNAUTHORIZED)
//                    .entity(mapOf("error" to "Invalid credentials"))
//                    .build()
//            }
//    }
//
//    // Logout action
//    @GET
//    @Path("/logout")
//    @Produces(MediaType.TEXT_HTML)
//    fun logout(): Response {
//        // In a real app, you'd invalidate the token on the server side
//        return Response.temporaryRedirect(java.net.URI.create("/admin/login")).build()
//    }
//
//    // Dashboard
//    @GET
//    @Path("/dashboard")
//    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("ADMIN")
//    fun getDashboard(): Response {
//        return Response.ok(templateService.renderAdminDashboard()).build()
//    }
//
//    // Projects list
//    @GET
//    @Path("/projects")
//    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("ADMIN")
//    fun getProjects(): Uni<Response> {
//        return templateService.renderAdminProjects()
//            .onItem().transform { content ->
//                Response.ok(content).build()
//            }
//    }
//
//    // New project form
//    @GET
//    @Path("/projects/new")
//    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("ADMIN")
//    fun getNewProjectForm(): Response {
//        return Response.ok(templateService.renderProjectForm()).build()
//    }
//
//    // Create project
//    @POST
//    @Path("/projects")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
//    fun createProject(project: Project): Uni<Response> {
//        return projectService.createProject(project)
//            .onItem().transform { savedProject ->
//                Response.ok(mapOf("id" to savedProject.id, "redirect" to "/admin/projects")).build()
//            }
//    }
//
//    // Edit project form
//    @GET
//    @Path("/projects/{id}/edit")
//    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("ADMIN")
//    fun getEditProjectForm(@PathParam("id") id: Long): Uni<Response> {
//        return projectService.getProjectById(id)
//            .onItem().transform { project ->
//                Response.ok(templateService.renderProjectForm(project)).build()
//            }
//            .onFailure().recoverWithItem(java.util.function.Function<Throwable, Response> {
//                Response.status(Response.Status.NOT_FOUND).entity("Project not found").build()
//            })
//    }
//
//    // Update project
//    @PUT
//    @Path("/projects/{id}")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
//    fun updateProject(@PathParam("id") id: Long, project: Project): Uni<Response> {
//        return projectService.updateProject(id, project)
//            .onItem().transform { updatedProject ->
//                Response.ok(mapOf("id" to updatedProject.id, "redirect" to "/admin/projects")).build()
//            }
//            .onFailure().recoverWithItem(java.util.function.Function<Throwable, Response> {
//                Response.status(Response.Status.NOT_FOUND).entity("Project not found").build()
//            })
//    }
//
//    // Delete project
//    @DELETE
//    @Path("/projects/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
//    fun deleteProject(@PathParam("id") id: Long): Uni<Response> {
//        return projectService.deleteProject(id)
//            .onItem().transform { success ->
//                if (success) {
//                    Response.ok(mapOf("success" to true)).build()
//                } else {
//                    Response.status(Response.Status.NOT_FOUND).entity(mapOf("success" to false)).build()
//                }
//            }
//    }
//
//    // Blog posts list
//    @GET
//    @Path("/blog")
//    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("ADMIN")
//    fun getBlogPosts(): Uni<Response> {
//        return templateService.renderAdminBlogPosts()
//            .onItem().transform { content ->
//                Response.ok(content).build()
//            }
//    }
//
//    // New blog post form
//    @GET
//    @Path("/blog/new")
//    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("ADMIN")
//    fun getNewBlogPostForm(): Response {
//        return Response.ok(templateService.renderBlogPostForm()).build()
//    }
//
//    // Create blog post
//    @POST
//    @Path("/blog")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
//    fun createBlogPost(blogPost: BlogPost): Uni<Response> {
//        return blogService.createBlogPost(blogPost)
//            .onItem().transform { savedPost ->
//                Response.ok(mapOf("id" to savedPost.id, "redirect" to "/admin/blog")).build()
//            }
//    }
//
//    // Edit blog post form
//    @GET
//    @Path("/blog/{id}/edit")
//    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("ADMIN")
//    fun getEditBlogPostForm(@PathParam("id") id: Long): Uni<Response> {
//        return blogService.getBlogPostById(id)
//            .onItem().transform { post ->
//                Response.ok(templateService.renderBlogPostForm(post)).build()
//            }
//            .onFailure().recoverWithItem(java.util.function.Function<Throwable, Response> {
//                Response.status(Response.Status.NOT_FOUND).entity("Blog post not found").build()
//            })
//    }
//
//    // Update blog post
//    @PUT
//    @Path("/blog/{id}")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
//    fun updateBlogPost(@PathParam("id") id: Long, blogPost: BlogPost): Uni<Response> {
//        return blogService.updateBlogPost(id, blogPost)
//            .onItem().transform { updatedPost ->
//                Response.ok(mapOf("id" to updatedPost.id, "redirect" to "/admin/blog")).build()
//            }
//            .onFailure().recoverWithItem(java.util.function.Function<Throwable, Response> {
//                Response.status(Response.Status.NOT_FOUND).entity("Blog post not found").build()
//            })
//    }
//
//    // Delete blog post
//    @DELETE
//    @Path("/blog/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
//    fun deleteBlogPost(@PathParam("id") id: Long): Uni<Response> {
//        return blogService.deleteBlogPost(id)
//            .onItem().transform { success ->
//                if (success) {
//                    Response.ok(mapOf("success" to true)).build()
//                } else {
//                    Response.status(Response.Status.NOT_FOUND).entity(mapOf("success" to false)).build()
//                }
//            }
//    }
//}
