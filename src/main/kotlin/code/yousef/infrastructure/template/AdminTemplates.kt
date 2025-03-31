package code.yousef.infrastructure.template

import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.time.LocalDateTime

@ApplicationScoped
class AdminTemplates {

    @Location("admin/base-layout.html")
    @Inject
    lateinit var baseTemplate: Template

    @Location("admin/login.html")
    @Inject
    lateinit var loginTemplate: Template

    fun buildLoginPage(): String? {
        return loginTemplate.instance().render()
    }

    fun buildDashboard(): String? {
        val content = buildDashboardContent()
        return baseTemplate.data("content", content).render()
    }

    private fun buildDashboardContent(): String {
        return StringBuilder().appendHTML().div {
            h2 { +"Admin Dashboard" }
            div(classes = "admin-grid") {
                // Dashboard widgets here
                div(classes = "admin-card") {
                    h3 { +"Projects" }
                    p { +"Manage your portfolio projects." }
                    div(classes = "card-actions") {
                        a(href = "/admin/projects", classes = "admin-btn primary-btn") { +"View Projects" }
                    }
                }
                div(classes = "admin-card") {
                    h3 { +"Blog Posts" }
                    p { +"Manage your blog content." }
                    div(classes = "card-actions") {
                        a(href = "/admin/blog", classes = "admin-btn primary-btn") { +"View Posts" }
                    }
                }
            }
        }.toString()
    }

    fun buildProjectsPage(projectEntities: List<ProjectEntity>): String? {
        val content = buildProjectsContent(projectEntities)
        return baseTemplate.data("content", content).render()
    }

    private fun buildProjectsContent(projectEntities: List<ProjectEntity>): String {
        return StringBuilder().appendHTML().div {
            h2 { +"Projects" }
            div(classes = "admin-actions") {
                a(href = "/admin/projects/new", classes = "admin-btn primary-btn") {
                    +"Create New Project"
                }
            }
            table(classes = "table") {
                thead {
                    tr {
                        th { +"Title" }
                        th { +"Featured" }
                        th { +"Actions" }
                    }
                }
                tbody {
                    projectEntities.forEach { project ->
                        tr {
                            td { +project.title }
                            td { +(if (project.featured) "Yes" else "No") }
                            td(classes = "table-actions") {
                                a(
                                    href = "/admin/projects/${project.id}/edit",
                                    classes = "admin-btn secondary-btn small"
                                ) {
                                    +"Edit"
                                }
                                button(classes = "admin-btn delete-btn small delete-project") {
                                    attributes["data-id"] = project.id.toString()
                                    +"Delete"
                                }
                            }
                        }
                    }
                }
            }
        }.toString()
    }

    fun buildBlogPostsPage(blogPostEntities: List<BlogPostEntity>): String? {
        val content = buildBlogPostsContent(blogPostEntities)
        return baseTemplate.data("content", content).render()
    }

    private fun buildBlogPostsContent(blogPostEntities: List<BlogPostEntity>): String {
        return StringBuilder().appendHTML().div {
            h2 { +"Blog Posts" }
            div(classes = "admin-actions") {
                a(href = "/admin/blog/new", classes = "admin-btn primary-btn") {
                    +"Create New Blog Post"
                }
            }
            table(classes = "table") {
                thead {
                    tr {
                        th { +"Title" }
                        th { +"Published" }
                        th { +"Date" }
                        th { +"Actions" }
                    }
                }
                tbody {
                    blogPostEntities.forEach { post ->
                        tr {
                            td { +post.title }
                            td {
                                span(classes = "status-badge ${if (post.published) "published" else "draft"}") {
                                    +(if (post.published) "Published" else "Draft")
                                }
                            }
                            td { +formatDate(post.publishDate) }
                            td(classes = "table-actions") {
                                a(href = "/admin/blog/${post.id}/edit", classes = "admin-btn secondary-btn small") {
                                    +"Edit"
                                }
                                button(classes = "admin-btn delete-btn small delete-post") {
                                    attributes["data-id"] = post.id.toString()
                                    +"Delete"
                                }
                            }
                        }
                    }
                }
            }
        }.toString()
    }

    private fun formatDate(dateTime: LocalDateTime): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern("MMM d, yyyy h:mm a")
        return dateTime.format(formatter)
    }

    fun buildProjectForm(projectEntity: ProjectEntity?): String? {
        val content = buildProjectFormContent(projectEntity)
        return baseTemplate.data("content", content).render()
    }

    private fun buildProjectFormContent(projectEntity: ProjectEntity?): String {
        val isNew = projectEntity == null
        val project = projectEntity ?: ProjectEntity().apply {
            title = ""
            description = ""
            imageUrl = ""
            modelUrl = ""
            technologies = listOf()
            githubUrl = ""
            demoUrl = ""
            featured = false
        }

        return StringBuilder().appendHTML().div {
            h2 { +(if (isNew) "Create New Project" else "Edit Project") }
            form(
                action = if (isNew) "/admin/projects" else "/admin/projects/${project.id}",
                method = FormMethod.post,
                encType = FormEncType.multipartFormData,
                classes = "admin-form"
            ) {
                id = "projectForm"

                div(classes = "form-group") {
                    label {
                        htmlFor = "title"
                        +"Title"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "title"
                        name = "title"
                        value = project.title
                        required = true
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "description"
                        +"Description"
                    }
                    textArea(classes = "admin-textarea") {
                        id = "description"
                        name = "description"
                        +project.description
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "imageUrl"
                        +"Image URL"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "imageUrl"
                        name = "imageUrl"
                        value = project.imageUrl
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "modelUrl"
                        +"Model URL"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "modelUrl"
                        name = "modelUrl"
                        value = project.modelUrl
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "githubUrl"
                        +"GitHub URL"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "githubUrl"
                        name = "githubUrl"
                        value = project.githubUrl
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "demoUrl"
                        +"Demo URL"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "demoUrl"
                        name = "demoUrl"
                        value = project.demoUrl
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "technologies"
                        +"Technologies (comma separated)"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "technologies"
                        name = "technologies"
                        value = project.technologies.joinToString(", ")
                        placeholder = "Kotlin, JavaScript, React, etc."
                    }
                }

                div(classes = "checkbox-group") {
                    input(type = InputType.checkBox) {
                        id = "featured"
                        name = "featured"
                        checked = project.featured
                    }
                    label(classes = "checkbox-label") {
                        htmlFor = "featured"
                        +"Featured"
                    }
                }

                div(classes = "form-actions") {
                    if (!isNew) {
                        input(type = InputType.hidden, name = "_method") {
                            value = "put"
                        }
                    }
                    button(type = ButtonType.submit, classes = "admin-btn primary-btn") {
                        +(if (isNew) "Create" else "Update")
                    }
                    a(href = "/admin/projects", classes = "admin-btn secondary-btn") {
                        +"Cancel"
                    }
                }
            }
        }.toString()
    }

    fun buildBlogPostForm(blogPostEntity: BlogPostEntity?): String? {
        val content = buildBlogPostFormContent(blogPostEntity)
        return baseTemplate.data("content", content).render()
    }

    private fun buildBlogPostFormContent(blogPostEntity: BlogPostEntity?): String {
        val isNew = blogPostEntity == null
        val post = blogPostEntity ?: BlogPostEntity().apply {
            title = ""
            summary = ""
            content = ""
            imageUrl = ""
            tags = listOf()
            publishDate = LocalDateTime.now()
            published = false
            slug = ""
        }

        return StringBuilder().appendHTML().div {
            h2 { +(if (isNew) "Create New Blog Post" else "Edit Blog Post") }
            form(
                action = if (isNew) "/admin/blog" else "/admin/blog/${post.id}",
                method = FormMethod.post,
                encType = FormEncType.multipartFormData,
                classes = "admin-form"
            ) {
                id = "blogPostForm"

                div(classes = "form-group") {
                    label {
                        htmlFor = "title"
                        +"Title"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "title"
                        name = "title"
                        value = post.title
                        required = true
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "slug"
                        +"Slug"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "slug"
                        name = "slug"
                        value = post.slug
                        required = true
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "summary"
                        +"Summary"
                    }
                    textArea(classes = "admin-textarea") {
                        id = "summary"
                        name = "summary"
                        +post.summary
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "content"
                        +"Content (Markdown)"
                    }
                    textArea(classes = "admin-textarea markdown-editor") {
                        id = "content"
                        name = "content"
                        +post.content
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "imageUrl"
                        +"Image URL"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "imageUrl"
                        name = "imageUrl"
                        value = post.imageUrl
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "tags"
                        +"Tags (comma separated)"
                    }
                    input(type = InputType.text, classes = "admin-input") {
                        id = "tags"
                        name = "tags"
                        value = post.tags.joinToString(", ")
                    }
                }

                div(classes = "form-group") {
                    label {
                        htmlFor = "publishDate"
                        +"Publish Date"
                    }
                    input(type = InputType.dateTimeLocal, classes = "admin-input") {
                        id = "publishDate"
                        name = "publishDate"
                        value = post.publishDate.toString().replace(" ", "T")
                    }
                }

                div(classes = "checkbox-group") {
                    input(type = InputType.checkBox) {
                        id = "published"
                        name = "published"
                        checked = post.published
                    }
                    label(classes = "checkbox-label") {
                        htmlFor = "published"
                        +"Published"
                    }
                }

                div(classes = "form-actions") {
                    if (!isNew) {
                        input(type = InputType.hidden, name = "_method") {
                            value = "put"
                        }
                    }
                    button(type = ButtonType.submit, classes = "admin-btn primary-btn") {
                        +(if (isNew) "Create" else "Update")
                    }
                    a(href = "/admin/blog", classes = "admin-btn secondary-btn") {
                        +"Cancel"
                    }
                }
            }
        }.toString()
    }
}