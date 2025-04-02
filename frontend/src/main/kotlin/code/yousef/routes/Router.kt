package code.yousef.routes

import code.yousef.pages.*
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.get

/**
 * Client-side router for handling navigation
 */
class Router {
    private lateinit var contentElement: HTMLElement
    private var currentPath = window.location.pathname

    /**
     * Initialize the router with the content element
     */
    fun initialize(contentElement: HTMLElement) {
        this.contentElement = contentElement

        // Handle browser back/forward navigation
        window.onpopstate = { event ->
            event?.let {
                val path = window.location.pathname
                renderPage(path)
                updateActiveNavLink(path)
            }
        }

        // Render the initial page
        renderPage(currentPath)
    }

    /**
     * Navigate to a new page
     */
    fun navigateTo(path: String) {
        if (path == currentPath) return

        // Update browser history
        window.history.pushState(null, "", path)

        // Update current path
        currentPath = path

        // Render the new page
        renderPage(path)

        // Update active nav link
        updateActiveNavLink(path)
    }

    /**
     * Render the page based on the current path
     */
    private fun renderPage(path: String) {
        // Clear the content element
        contentElement.innerHTML = ""

        // Route to the appropriate page
        when {
            path == "/" -> HomePage(contentElement)
            path == "/projects" -> ProjectsPage(contentElement)
            path.startsWith("/projects/") -> {
                val id = path.substringAfterLast("/")
                ProjectDetailPage(contentElement, id)
            }
            path == "/blog" -> BlogPage(contentElement)
            path.startsWith("/blog/tag/") -> {
                val tag = path.substringAfterLast("/")
                BlogTagPage(contentElement, tag)
            }
            path.startsWith("/blog/") -> {
                val slug = path.substringAfterLast("/")
                BlogPostPage(contentElement, slug)
            }
            path == "/services" -> ServicesPage(contentElement)
            path.startsWith("/services/") -> {
                val id = path.substringAfterLast("/")
                ServiceDetailPage(contentElement, id)
            }
            path == "/about" -> AboutPage(contentElement)
            path == "/contact" -> ContactPage(contentElement)
            else -> NotFoundPage(contentElement)
        }

        // Scroll to top on page change
        window.scrollTo(0.0, 0.0)
    }

    /**
     * Update the active navigation link
     */
    private fun updateActiveNavLink(path: String) {
        // Remove active class from all nav links
        document.querySelectorAll(".nav-link").forEach { element ->
            element.classList.remove("active")
        }

        // Add active class to the current nav link
        val navLinks = document.querySelectorAll(".nav-link")
        for (i in 0 until navLinks.length) {
            val link = navLinks[i] as HTMLElement
            val href = link.getAttribute("href") ?: ""

            if ((path == "/" && href == "/") ||
                (path != "/" && path.startsWith(href) && href != "/")) {
                link.classList.add("active")
            }
        }
    }
}