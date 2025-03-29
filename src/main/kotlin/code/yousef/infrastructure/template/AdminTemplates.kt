package code.yousef.infrastructure.template

import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

object AdminTemplates {
    fun buildLoginPage(): StringBuilder = StringBuilder().appendHTML().html {
        head {
            meta(charset = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title("Login - Admin")

            // Fonts
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700&display=swap"
            )

            // CSS
            styleLink("/css/main.css")
            styleLink("/css/admin.css")
        }
        body {
            div("login-container") {
                div("login-form-container") {
                    div("login-header") {
                        h1 { +"ADMIN LOGIN" }
                    }

                    form {
                        id = "login-form"
                        attributes["hx-post"] = "/admin/login"
                        attributes["hx-redirect"] = "/admin/dashboard"

                        div("form-group") {
                            label { +"USERNAME" }
                            input(type = InputType.text, name = "username") {
                                required = true
                                classes = setOf("admin-input")
                            }
                        }

                        div("form-group") {
                            label { +"PASSWORD" }
                            input(type = InputType.password, name = "password") {
                                required = true
                                classes = setOf("admin-input")
                            }
                        }

                        button(type = ButtonType.submit) {
                            classes = setOf("admin-btn", "primary-btn", "full-width")
                            +"LOGIN"
                        }
                    }

                    div("login-footer") {
                        a(href = "/") { +"← BACK TO SITE" }
                    }
                }

                // HTMX
                script(src = "/js/vendors.bundle.js") { defer = true }
                script(src = "/js/admin.bundle.js") { defer = true }
            }
        }.toString()
    }

    fun buildDashboard(): StringBuilder = StringBuilder().appendHTML().html {
        head {
            meta(charset = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title("Dashboard - Admin")

            // Fonts
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700&display=swap"
            )

            // CSS
            styleLink("/css/main.css")
            styleLink("/css/admin.css")

            // HTMX
            script(src = "/js/vendors.bundle.js") { defer = true }
            script(src = "/js/admin.bundle.js") { defer = true }
        }
        body("admin") {
            div("admin-container") {
                // Sidebar
                div("admin-sidebar") {
                    div("admin-logo") {
                        h1 { +"ADMIN PANEL" }
                    }

                    nav("admin-nav") {
                        ul {
                            li {
                                a(href = "/admin/dashboard", classes = "active") {
                                    +"Dashboard"
                                }
                            }
                            li {
                                a(href = "/admin/projects") {
                                    +"Projects"
                                }
                            }
                            li {
                                a(href = "/admin/blog") {
                                    +"Blog Posts"
                                }
                            }
                            li {
                                a(href = "/admin/logout") {
                                    +"Logout"
                                }
                            }
                        }
                    }
                }

                // Content
                div("admin-content") {
                    div("admin-header") {
                        h2 { +"Dashboard" }
                    }

                    div("admin-grid") {
                        div("admin-card") {
                            h3 { +"Projects" }
                            p { +"Manage your portfolio projects" }
                            div("card-actions") {
                                a(href = "/admin/projects", classes = "admin-btn secondary-btn") {
                                    +"View All"
                                }
                                a(href = "/admin/projects/new", classes = "admin-btn primary-btn") {
                                    +"Add New"
                                }
                            }
                        }

                        div("admin-card") {
                            h3 { +"Blog Posts" }
                            p { +"Manage your blog content" }
                            div("card-actions") {
                                a(href = "/admin/blog", classes = "admin-btn secondary-btn") {
                                    +"View All"
                                }
                                a(href = "/admin/blog/new", classes = "admin-btn primary-btn") {
                                    +"Add New"
                                }
                            }
                        }
                    }
                }
            }
        }.toString()
    }

    fun buildProjectsPage(projectEntities: List<ProjectEntity>): StringBuilder = StringBuilder().appendHTML().html {
        head {
            meta(charset = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title("Projects - Admin")

            // Fonts
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700&display=swap"
            )

            // CSS
            styleLink("/css/main.css")
            styleLink("/css/admin.css")

            // HTMX
            script(src = "/js/vendors.bundle.js") { defer = true }
            script(src = "/js/admin.bundle.js") { defer = true }
        }
        body("admin") {
            div("admin-container") {
                // Sidebar
                div("admin-sidebar") {
                    div("admin-logo") {
                        h1 { +"ADMIN PANEL" }
                    }

                    nav("admin-nav") {
                        ul {
                            li {
                                a(href = "/admin/dashboard") {
                                    +"Dashboard"
                                }
                            }
                            li {
                                a(href = "/admin/projects", classes = "active") {
                                    +"Projects"
                                }
                            }
                            li {
                                a(href = "/admin/blog") {
                                    +"Blog Posts"
                                }
                            }
                            li {
                                a(href = "/admin/logout") {
                                    +"Logout"
                                }
                            }
                        }
                    }

                    // Content
                    div("admin-content") {
                        div("admin-header") {
                            h2 { +"Projects" }

                            a(href = "/admin/projects/new", classes = "admin-btn primary-btn") {
                                +"Add New Project"
                            }
                        }

                        div("admin-card") {
                            table("table") {
                                thead {
                                    tr {
                                        th { +"Title" }
                                        th { +"Technologies" }
                                        th { +"Featured" }
                                        th { +"Actions" }
                                    }
                                }
                                tbody {
                                    projectEntities.forEach { project ->
                                        tr {
                                            td { +project.title }
                                            td { +project.technologies.joinToString(", ") }
                                            td { +if (project.featured) "Yes" else "No" }
                                            td {
                                                div("table-actions") {
                                                    a(
                                                        href = "/admin/projects/${project.id}/edit",
                                                        classes = "admin-btn secondary-btn small"
                                                    ) {
                                                        +"Edit"
                                                    }
                                                    button {
                                                        classes = setOf("admin-btn", "delete-btn", "small")
                                                        attributes["hx-delete"] = "/admin/projects/${project.id}"
                                                        attributes["hx-confirm"] =
                                                            "Are you sure you want to delete this project?"
                                                        attributes["hx-target"] = "closest tr"
                                                        attributes["hx-swap"] = "outerHTML"
                                                        +"Delete"
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }.toString()
        }
    }

    fun buildBlogPostsPage(posts: List<BlogPostEntity>): StringBuilder = StringBuilder().appendHTML().html {
        head {
            meta(charset = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title("Blog Posts - Admin")

            // Fonts
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700&display=swap"
            )

            // CSS
            styleLink("/css/main.css")
            styleLink("/css/admin.css")

            // HTMX
            script(src = "/js/vendors.bundle.js") { defer = true }
            script(src = "/js/admin.bundle.js") { defer = true }
        }
        body("admin") {
            div("admin-container") {
                // Sidebar
                div("admin-sidebar") {
                    div("admin-logo") {
                        h1 { +"ADMIN PANEL" }
                    }

                    nav("admin-nav") {
                        ul {
                            li {
                                a(href = "/admin/dashboard") {
                                    +"Dashboard"
                                }
                            }
                            li {
                                a(href = "/admin/projects") {
                                    +"Projects"
                                }
                            }
                            li {
                                a(href = "/admin/blog", classes = "active") {
                                    +"Blog Posts"
                                }
                            }
                            li {
                                a(href = "/admin/logout") {
                                    +"Logout"
                                }
                            }
                        }
                    }

                    // Content
                    div("admin-content") {
                        div("admin-header") {
                            h2 { +"Blog Posts" }

                            a(href = "/admin/blog/new", classes = "admin-btn primary-btn") {
                                +"Add New Post"
                            }
                        }

                        div("admin-card") {
                            table("table") {
                                thead {
                                    tr {
                                        th { +"Title" }
                                        th { +"Status" }
                                        th { +"Date" }
                                        th { +"Actions" }
                                    }
                                }
                                tbody {
                                    posts.forEach { post ->
                                        tr {
                                            td { +post.title }
                                            td {
                                                span("status-badge ${if (post.published) "published" else "draft"}") {
                                                    +(if (post.published) "Published" else "Draft")
                                                }
                                            }
                                            td { +post.updatedAt.toString() }
                                            td {
                                                div("table-actions") {
                                                    a(
                                                        href = "/admin/blog/${post.id}/edit",
                                                        classes = "admin-btn secondary-btn small"
                                                    ) {
                                                        +"Edit"
                                                    }
                                                    a(
                                                        href = "/blog/${post.slug}",
                                                        target = "_blank",
                                                        classes = "admin-btn secondary-btn small"
                                                    ) {
                                                        +"View"
                                                    }
                                                    button {
                                                        classes = setOf("admin-btn", "delete-btn", "small")
                                                        attributes["hx-delete"] = "/admin/blog/${post.id}"
                                                        attributes["hx-confirm"] =
                                                            "Are you sure you want to delete this post?"
                                                        attributes["hx-target"] = "closest tr"
                                                        attributes["hx-swap"] = "outerHTML"
                                                        +"Delete"
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }.toString()
        }
    }

    fun buildProjectForm(projectEntity: ProjectEntity? = null): StringBuilder = StringBuilder().appendHTML().html {
        head {
            meta(charset = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title("${if (projectEntity == null) "New" else "Edit"} Project - Admin")

            // Fonts
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700&display=swap"
            )

            // CSS
            styleLink("/css/main.css")
            styleLink("/css/admin.css")

            // HTMX
            script(src = "/js/vendors.bundle.js") { defer = true }
            script(src = "/js/admin.bundle.js") { defer = true }
        }
        body("admin") {
            div("admin-container") {
                // Sidebar
                div("admin-sidebar") {
                    div("admin-logo") {
                        h1 { +"ADMIN PANEL" }
                    }

                    nav("admin-nav") {
                        ul {
                            li {
                                a(href = "/admin/dashboard") {
                                    +"Dashboard"
                                }
                            }
                            li {
                                a(href = "/admin/projects", classes = "active") {
                                    +"Projects"
                                }
                            }
                            li {
                                a(href = "/admin/blog") {
                                    +"Blog Posts"
                                }
                            }
                            li {
                                a(href = "/admin/logout") {
                                    +"Logout"
                                }
                            }
                        }
                    }

                    // Content
                    div("admin-content") {
                        div("admin-header") {
                            h2 { +"${if (projectEntity == null) "New" else "Edit"} Project" }
                        }

                        div("admin-card") {
                            form("admin-form") {
                                attributes["hx-${if (projectEntity == null) "post" else "put"}"] =
                                    if (projectEntity == null) "/admin/projects" else "/admin/projects/${projectEntity.id}"
                                attributes["hx-redirect"] = "/admin/projects"

                                div("form-group") {
                                    label { +"Title" }
                                    input(type = InputType.text, name = "title") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (projectEntity != null) {
                                            value = projectEntity.title
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Description" }
                                    textArea {
                                        name = "description"
                                        required = true
                                        classes = setOf("admin-textarea")
                                        if (projectEntity != null) {
                                            +projectEntity.description
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Technologies (comma separated)" }
                                    input(type = InputType.text, name = "technologies") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (projectEntity != null) {
                                            value = projectEntity.technologies.joinToString(", ")
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Image URL" }
                                    input(type = InputType.text, name = "imageUrl") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (projectEntity != null) {
                                            value = projectEntity.imageUrl
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"3D Model URL" }
                                    input(type = InputType.text, name = "modelUrl") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (projectEntity != null) {
                                            value = projectEntity.modelUrl
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"GitHub URL" }
                                    input(type = InputType.text, name = "githubUrl") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (projectEntity != null) {
                                            value = projectEntity.githubUrl
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Demo URL" }
                                    input(type = InputType.text, name = "demoUrl") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (projectEntity != null) {
                                            value = projectEntity.demoUrl
                                        }
                                    }
                                }

                                div("form-group checkbox-group") {
                                    input(type = InputType.checkBox, name = "featured") {
                                        id = "featured"
                                        if (projectEntity != null && projectEntity.featured) {
                                            checked = true
                                        }
                                    }
                                    label(classes = "checkbox-label") {
                                        htmlFor = "featured"
                                        +"Featured Project"
                                    }
                                }

                                div("form-actions") {
                                    button(type = ButtonType.submit) {
                                        classes = setOf("admin-btn", "primary-btn")
                                        +"Save Project"
                                    }
                                    a(href = "/admin/projects", classes = "admin-btn secondary-btn") {
                                        +"Cancel"
                                    }
                                }
                            }
                        }
                    }
                }
            }.toString()
        }
    }

    fun buildBlogPostForm(blogPostEntity: BlogPostEntity? = null): StringBuilder = StringBuilder().appendHTML().html {
        head {
            meta(charset = "utf-8")
            meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
            title("${if (blogPostEntity == null) "New" else "Edit"} Blog Post - Admin")

            // Fonts
            link(
                rel = "stylesheet",
                href = "https://fonts.googleapis.com/css2?family=Space+Grotesk:wght@300;400;500;600;700&display=swap"
            )

            // CSS
            styleLink("/css/main.css")
            styleLink("/css/admin.css")

            // HTMX
            script(src = "/js/vendors.bundle.js") { defer = true }
            script(src = "/js/admin.bundle.js") { defer = true }
        }
        body("admin") {
            div("admin-container") {
                // Sidebar
                div("admin-sidebar") {
                    div("admin-logo") {
                        h1 { +"ADMIN PANEL" }
                    }

                    nav("admin-nav") {
                        ul {
                            li {
                                a(href = "/admin/dashboard") {
                                    +"Dashboard"
                                }
                            }
                            li {
                                a(href = "/admin/projects") {
                                    +"Projects"
                                }
                            }
                            li {
                                a(href = "/admin/blog", classes = "active") {
                                    +"Blog Posts"
                                }
                            }
                            li {
                                a(href = "/admin/logout") {
                                    +"Logout"
                                }
                            }
                        }
                    }

                    // Content
                    div("admin-content") {
                        div("admin-header") {
                            h2 { +"${if (blogPostEntity == null) "New" else "Edit"} Blog Post" }
                        }

                        div("admin-card") {
                            form("admin-form") {
                                attributes["hx-${if (blogPostEntity == null) "post" else "put"}"] =
                                    if (blogPostEntity == null) "/admin/blog" else "/admin/blog/${blogPostEntity.id}"
                                attributes["hx-redirect"] = "/admin/blog"

                                div("form-group") {
                                    label { +"Title" }
                                    input(type = InputType.text, name = "title") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (blogPostEntity != null) {
                                            value = blogPostEntity.title
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Slug (optional)" }
                                    input(type = InputType.text, name = "slug") {
                                        classes = setOf("admin-input")
                                        placeholder = "auto-generated-if-empty"
                                        if (blogPostEntity != null) {
                                            value = blogPostEntity.slug
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Summary" }
                                    textArea {
                                        name = "summary"
                                        required = true
                                        classes = setOf("admin-textarea")
                                        attributes["maxlength"] = "500"
                                        if (blogPostEntity != null) {
                                            +blogPostEntity.summary
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Content" }
                                    textArea {
                                        name = "content"
                                        required = true
                                        classes = setOf("admin-textarea", "content-editor")
                                        if (blogPostEntity != null) {
                                            +blogPostEntity.content
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Tags (comma separated)" }
                                    input(type = InputType.text, name = "tags") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (blogPostEntity != null) {
                                            value = blogPostEntity.tags.joinToString(", ")
                                        }
                                    }
                                }

                                div("form-group") {
                                    label { +"Image URL" }
                                    input(type = InputType.text, name = "imageUrl") {
                                        required = true
                                        classes = setOf("admin-input")
                                        if (blogPostEntity != null) {
                                            value = blogPostEntity.imageUrl
                                        }
                                    }
                                }

                                div("form-group checkbox-group") {
                                    input(type = InputType.checkBox, name = "published") {
                                        id = "published"
                                        if (blogPostEntity != null && blogPostEntity.published) {
                                            checked = true
                                        }
                                    }
                                    label(classes = "checkbox-label") {
                                        htmlFor = "published"
                                        +"Published"
                                    }
                                }

                                div("form-actions") {
                                    button(type = ButtonType.submit) {
                                        classes = setOf("admin-btn", "primary-btn")
                                        +"Save Blog Post"
                                    }
                                    a(href = "/admin/blog", classes = "admin-btn secondary-btn") {
                                        +"Cancel"
                                    }
                                }
                            }
                        }
                    }
                }
            }.toString()
        }
    }
}
