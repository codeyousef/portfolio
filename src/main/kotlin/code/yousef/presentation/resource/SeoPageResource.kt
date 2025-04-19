package code.yousef.presentation.resource

import code.yousef.api.ApiRoutes
import code.yousef.application.service.BlogService
import code.yousef.application.service.ProjectService
import code.yousef.application.service.ServiceService
import code.yousef.infrastructure.persistence.mapper.ProjectMapper
import code.yousef.infrastructure.qute.QuteComponents
import code.yousef.infrastructure.summon.SummonRenderer
import code.yousef.ui.AppRoot
import code.yousef.ui.HomePage
import io.quarkus.qute.Template
import io.quarkus.qute.Location
import java.util.UUID
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.jboss.logging.Logger
import java.util.*

@Path("/seo")
@ApplicationScoped
class SeoPageResource @Inject constructor(
    private val projectService: ProjectService,
    private val blogService: BlogService,
    private val serviceService: ServiceService,
    private val summonRenderer: SummonRenderer,
    private val quteComponents: QuteComponents,
    private val projectMapper: ProjectMapper,
    @Location("index.html")
    private val indexTemplate: Template
) {
    private val logger = Logger.getLogger(SeoPageResource::class.java)

    /**
     * Renders the initial HTML shell with critical CSS and metadata for SEO
     */
    private fun renderHtmlShell(
        title: String,
        description: String,
        ogImage: String = "/images/og-image.jpg",
        canonicalUrl: String = "",
        bodyContent: String
    ): String {
        return buildString {
            appendHTML().html {
                lang = "en"
                head {
                    meta(charset = "utf-8")
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    title(title)
                    meta(name = "description", content = description)

                    // Open Graph (OG) tags for social sharing
                    meta(name = "og:title", content = title)
                    meta(name = "og:description", content = description)
                    meta(name = "og:image", content = ogImage)
                    meta(name = "og:type", content = "website")

                    // Twitter Card tags
                    meta(name = "twitter:card", content = "summary_large_image")
                    meta(name = "twitter:title", content = title)
                    meta(name = "twitter:description", content = description)
                    meta(name = "twitter:image", content = ogImage)

                    // Canonical URL for SEO
                    if (canonicalUrl.isNotEmpty()) {
                        link(rel = "canonical", href = canonicalUrl)
                    }

                    // Inline critical CSS for faster page load
                    style {
                        unsafe {
                            +"""
                            :root {
                                --primary-rgb: 0, 247, 255;
                                --primary: rgb(var(--primary-rgb));
                                --secondary-rgb: 255, 42, 109;
                                --secondary: rgb(var(--secondary-rgb));
                                --dark: #0a0a14;
                                --light: #f5f5f7;
                            }
                            body {
                                font-family: 'Inter', system-ui, -apple-system, sans-serif;
                                margin: 0;
                                padding: 0;
                                background-color: var(--dark);
                                color: var(--light);
                                line-height: 1.6;
                            }
                            .container {
                                max-width: 1200px;
                                margin: 0 auto;
                                padding: 0 20px;
                            }
                            .loading {
                                display: flex;
                                align-items: center;
                                justify-content: center;
                                min-height: 100vh;
                                color: var(--primary);
                                font-size: 1.5rem;
                            }
                            """
                        }
                    }

                    // Preload main JavaScript bundle for faster execution
                    script(src = "/frontend.js") {
                        defer = true
                    }
                }
                body {
                    // Server-rendered initial HTML
                    if (bodyContent.isNotEmpty()) {
                        unsafe { +bodyContent }
                    } else {
                        // Loading indicator if no pre-rendered content
                        div(classes = "loading") {
                            +"Loading..."
                        }
                    }

                    // Application mount point
                    div {
                        id = "root"
                    }

                    // Structured data for SEO (JSON-LD)
                    script(type = "application/ld+json") {
                        unsafe {
                            +"""
                            {
                                "@context": "https://schema.org",
                                "@type": "WebSite",
                                "url": "https://yousef.codes",
                                "name": "$title",
                                "description": "$description"
                            }
                            """
                        }
                    }
                }
            }
        }
    }

    /**
     * Home page route - server-side rendered with Qute templates
     */
    @GET
    @Path(ApiRoutes.Pages.HOME)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getHomePage(): Response {
        println("SeoPageResource.getHomePage() called - rendering home page with Qute templates")
        println("QuteComponents instance: ${quteComponents.javaClass.name}")
        println("IndexTemplate instance: ${indexTemplate.javaClass.name}")

        try {
            println("Rendering index.html template with Qute")

            // Get featured projects for the projects section
            val projects = projectService.getFeaturedProjects()
            println("Retrieved ${projects.size} featured projects")

            // Convert projects to entities for the template
            val projectEntities = projects.map { project -> projectMapper.toEntity(project) }
            println("Converted ${projectEntities.size} projects to entities")

            // Render the index.html template with the data
            val html = indexTemplate.data(
                "title", "Yousef's Portfolio - Modern Software Development",
                "description", "Showcasing software development projects and services specializing in Kotlin, Quarkus, and modern web technologies.",
                "projects", projectEntities
            ).render()

            println("SeoPageResource.getHomePage() completed successfully, result length: ${html.length}")
            println("First 100 chars of HTML: ${html.take(100)}")

            println("Building Response object with HTML content")
            return Response.ok(html).build()
        } catch (e: Exception) {
            println("Error rendering home page with Qute: ${e}")
            println("Exception type: ${e.javaClass.name}")
            println("Exception message: ${e.message}")
            println("Stack trace: ${e.stackTraceToString()}")

            println("Building server error Response")
            return Response.serverError()
                .entity("<h1>Error rendering home page</h1><p>${e.message}</p><pre>${e.stackTraceToString()}</pre>")
                .type(MediaType.TEXT_HTML)
                .build()
        }
    }

    /**
     * Projects page route
     */
    @GET
    @Path(ApiRoutes.Pages.PROJECTS)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getProjectsPage(): Response {
        logger.info("Rendering projects page for SEO")

        try {
            val projects = projectService.getAllProjects()

            val initialContent = buildString {
                appendHTML().div {
                    id = "seo-content"
                    section(classes = "page-header") {
                        h1 { +"Projects" }
                        p { +"Explore my latest work showcasing innovative solutions and cutting-edge technologies." }
                    }

                    div(classes = "projects-grid") {
                        projects.forEach { project ->
                            article(classes = "project-card") {
                                h3 { +project.title }
                                p { +project.description }
                                div(classes = "technologies") {
                                    project.technologies.forEach { tech ->
                                        span { +tech }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            val html = renderHtmlShell(
                title = "Projects - Yousef's Portfolio",
                description = "Browse through my portfolio of software development projects featuring Kotlin, web applications, and more.",
                canonicalUrl = "https://yousef.codes/projects",
                ogImage = "/images/og-image.jpg",
                bodyContent = initialContent
            )

            return Response.ok(html).build()
        } catch (e: Exception) {
            logger.error("Error rendering projects page", e)
            return Response.serverError().build()
        }
    }

    /**
     * Single project details page
     */
    @GET
    @Path(ApiRoutes.Pages.PROJECT_DETAILS)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getProjectDetailsPage(@PathParam("id") id: UUID): Response {
        logger.info("Rendering project details page for ID: $id")

        try {
            val project = projectService.getProjectById(id) ?: return Response.status(Response.Status.NOT_FOUND).build()

            val initialContent = buildString {
                appendHTML().div {
                    attributes["id"] = "seo-content"
                    article(classes = "project-details") {
                        h1 { +project.title }

                        if (project.imageUrl.isNotEmpty()) {
                            img(src = project.imageUrl, alt = project.title)
                        }

                        section(classes = "project-description") {
                            h2 { +"Description" }
                            p { +project.description }
                        }

                        section(classes = "project-technologies") {
                            h2 { +"Technologies" }
                            ul {
                                project.technologies.forEach { tech ->
                                    li { +tech }
                                }
                            }
                        }

                        if (project.githubUrl.isNotEmpty() || project.demoUrl.isNotEmpty()) {
                            section(classes = "project-links") {
                                h2 { +"Links" }
                                if (project.githubUrl.isNotEmpty()) {
                                    p {
                                        +"GitHub: "
                                        a(href = project.githubUrl) { +project.githubUrl }
                                    }
                                }
                                if (project.demoUrl.isNotEmpty()) {
                                    p {
                                        +"Demo: "
                                        a(href = project.demoUrl) { +project.demoUrl }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            val html = renderHtmlShell(
                title = "${project.title} - Yousef's Portfolio",
                description = project.description,
                ogImage = project.imageUrl.takeIf { it.isNotEmpty() } ?: "/images/og-image.jpg",
                canonicalUrl = "https://yousef.codes/projects/${project.id}",
                bodyContent = initialContent
            )

            return Response.ok(html).build()
        } catch (e: Exception) {
            logger.error("Error rendering project details page", e)
            return Response.serverError().build()
        }
    }

    /**
     * Blog listing page
     */
    @GET
    @Path(ApiRoutes.Pages.BLOG)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getBlogPage(@QueryParam("page") @DefaultValue("0") page: Int): Response {
        logger.info("Rendering blog page for SEO")

        try {
            val blogs = blogService.getPublishedBlogs(page, 10)

            val initialContent = buildString {
                appendHTML().div {
                    id = "seo-content"
                    section(classes = "page-header") {
                        h1 { +"Blog" }
                        p { +"Thoughts and insights on software development, technology, and more." }
                    }

                    div(classes = "blog-grid") {
                        blogs.forEach { blog ->
                            article(classes = "blog-card") {
                                h2 { +blog.title }
                                p { +blog.summary }
                                div(classes = "blog-meta") {
                                    span(classes = "date") { +blog.publishDate.toString() }
                                    span(classes = "reading-time") { +"${blog.getReadingTime()} min read" }
                                }
                                div(classes = "tags") {
                                    blog.tags.forEach { tag ->
                                        span(classes = "tag") { +tag }
                                    }
                                }
                                a(href = "/blog/${blog.slug}") { +"Read more" }
                            }
                        }
                    }
                }
            }

            val html = renderHtmlShell(
                title = "Blog - Yousef's Portfolio",
                description = "Insights and articles about software development, Kotlin, web technologies, and more.",
                canonicalUrl = "https://yousef.codes/blog" + if (page > 0) "?page=$page" else "",
                ogImage = "/images/og-image.jpg",
                bodyContent = initialContent
            )

            return Response.ok(html).build()
        } catch (e: Exception) {
            logger.error("Error rendering blog page", e)
            return Response.serverError().build()
        }
    }

    /**
     * Single blog post page
     */
    @GET
    @Path(ApiRoutes.Pages.BLOG_POST)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getBlogPostPage(@PathParam("slug") slug: String): Response {
        logger.info("Rendering blog post page for slug: $slug")

        try {
            val blog = blogService.getBlogBySlug(slug) ?: return Response.status(Response.Status.NOT_FOUND).build()

            val initialContent = buildString {
                appendHTML().div {
                    id = "seo-content"
                    article(classes = "blog-post") {
                        h1 { +blog.title }

                        div(classes = "blog-meta") {
                            span(classes = "date") { +blog.publishDate!!.toString() }
                            span(classes = "reading-time") { +"${blog.getReadingTime()} min read" }
                        }

                        if (blog.imageUrl?.isNotEmpty() == true) {
                            img(src = blog.imageUrl!!, alt = blog.title)
                        }

                        section(classes = "blog-content") {
                            unsafe { +blog.content }
                        }

                        if (blog.tags.isNotEmpty()) {
                            div(classes = "tags") {
                                blog.tags.forEach { tag ->
                                    a(href = "/blog/tag/$tag", classes = "tag") { +tag }
                                }
                            }
                        }
                    }
                }
            }

            val html = renderHtmlShell(
                title = "${blog.title} - Yousef's Blog",
                description = blog.summary,
                ogImage = blog.imageUrl ?: "/images/og-image.jpg",
                canonicalUrl = "https://yousef.codes/blog/${blog.slug}",
                bodyContent = initialContent
            )

            return Response.ok(html).build()
        } catch (e: Exception) {
            logger.error("Error rendering blog post page", e)
            return Response.serverError().build()
        }
    }

    /**
     * Services page route
     */
    @GET
    @Path(ApiRoutes.Pages.SERVICES)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getServicesPage(): Response {
        logger.info("Rendering services page for SEO")

        try {
            val services = serviceService.getAllServices()

            val initialContent = buildString {
                appendHTML().div {
                    id = "seo-content"
                    section(classes = "page-header") {
                        h1 { +"Services" }
                        p { +"Professional services tailored to your business needs." }
                    }

                    div(classes = "services-grid") {
                        services.forEach { service ->
                            article(classes = "service-card") {
                                h3 { +service.title }
                                p { +service.shortDescription }

                                if (service.features.isNotEmpty()) {
                                    ul(classes = "service-features") {
                                        service.features.take(3).forEach { feature ->
                                            li { +feature }
                                        }
                                    }
                                }

                                if (service.price != null) {
                                    p(classes = "service-price") { +"${service.price}" }
                                }

                                a(href = service.detailsLink) { +"Learn more" }
                            }
                        }
                    }
                }
            }

            val html = renderHtmlShell(
                title = "Services - Yousef's Portfolio",
                description = "Professional software development services including web development, mobile apps, and more.",
                canonicalUrl = "https://yousef.codes/services",
                ogImage = "/images/og-image.jpg",
                bodyContent = initialContent
            )

            return Response.ok(html).build()
        } catch (e: Exception) {
            logger.error("Error rendering services page", e)
            return Response.serverError().build()
        }
    }

    /**
     * Single service details page
     */
    @GET
    @Path(ApiRoutes.Pages.SERVICE_DETAILS)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getServiceDetailsPage(@PathParam("id") id: UUID): Response {
        logger.info("Rendering service details page for ID: $id")

        try {
            val service = serviceService.getServiceById(id) ?: return Response.status(Response.Status.NOT_FOUND).build()

            val initialContent = buildString {
                appendHTML().div {
                    attributes["id"] = "seo-content"
                    article(classes = "service-details") {
                        h1 { +service.title }

                        section(classes = "service-description") {
                            h2 { +"Description" }
                            p { +service.shortDescription }
                            unsafe { +service.fullDescription }
                        }

                        if (service.features.isNotEmpty()) {
                            section(classes = "service-features") {
                                h2 { +"Features" }
                                ul {
                                    service.features.forEach { feature ->
                                        li { +feature }
                                    }
                                }
                            }
                        }

                        if (service.price != null) {
                            section(classes = "service-pricing") {
                                h2 { +"Pricing" }
                                p { +"Starting at ${service.price}" }
                            }
                        }

                        section(classes = "service-cta") {
                            a(href = service.ctaLink, classes = "cta-button") { +service.ctaText }
                        }
                    }
                }
            }

            val html = renderHtmlShell(
                title = "${service.title} - Yousef's Services",
                description = service.shortDescription,
                canonicalUrl = "https://yousef.codes/services/${service.id}",
                ogImage = "/images/og-image.jpg",
                bodyContent = initialContent
            )

            return Response.ok(html).build()
        } catch (e: Exception) {
            logger.error("Error rendering service details page", e)
            return Response.serverError().build()
        }
    }

    /**
     * About page
     */
    @GET
    @Path(ApiRoutes.Pages.ABOUT)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getAboutPage(): Response {
        logger.info("Rendering about page for SEO")

        val initialContent = buildString {
            appendHTML().div {
                id = "seo-content"
                section(classes = "page-header") {
                    h1 { +"About Me" }
                }

                article(classes = "about-content") {
                    p { +"Software developer with expertise in Kotlin, Quarkus, and modern web technologies." }

                    section {
                        h2 { +"Skills" }
                        ul {
                            li { +"Kotlin & Java" }
                            li { +"Quarkus & Spring" }
                            li { +"Web Development" }
                            li { +"Mobile Development" }
                        }
                    }

                    section {
                        h2 { +"Experience" }
                        // Experience details would go here
                    }
                }
            }
        }

        val html = renderHtmlShell(
            title = "About - Yousef's Portfolio",
            description = "Learn more about Yousef, a software developer specializing in Kotlin and modern web technologies.",
            canonicalUrl = "https://yousef.codes/about",
            ogImage = "/images/og-image.jpg",
            bodyContent = initialContent
        )

        return Response.ok(html).build()
    }

    /**
     * Contact page
     */
    @GET
    @Path(ApiRoutes.Pages.CONTACT)
    @Produces(MediaType.TEXT_HTML)
    suspend fun getContactPage(): Response {
        logger.info("Rendering contact page for SEO")

        val initialContent = buildString {
            appendHTML().div {
                id = "seo-content"
                section(classes = "page-header") {
                    h1 { +"Contact Me" }
                    p { +"Get in touch for inquiries, project discussions, or collaboration opportunities." }
                }

                section(classes = "contact-section") {
                    form(classes = "contact-form") {
                        div(classes = "form-group") {
                            label { +"Name" }
                            input(type = InputType.text, name = "name")
                        }
                        div(classes = "form-group") {
                            label { +"Email" }
                            input(type = InputType.email, name = "email")
                        }
                        div(classes = "form-group") {
                            label { +"Message" }
                            textArea(classes = "form-textarea") {
                                name = "message"
                            }
                        }
                        button(type = ButtonType.submit) { +"Send Message" }
                    }

                    div(classes = "contact-info") {
                        h2 { +"Contact Information" }
                        p { +"Email: contact@yousef.codes" }
                        p { +"Location: New York, NY" }
                    }
                }
            }
        }

        val html = renderHtmlShell(
            title = "Contact - Yousef's Portfolio",
            description = "Contact Yousef for software development services, project inquiries, or collaboration opportunities.",
            canonicalUrl = "https://yousef.codes/contact",
            ogImage = "/images/og-image.jpg",
            bodyContent = initialContent
        )

        return Response.ok(html).build()
    }
}
