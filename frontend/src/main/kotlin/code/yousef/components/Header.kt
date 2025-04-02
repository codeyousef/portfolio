package code.yousef.components

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.header
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement

/**
 * App header component with navigation
 */
fun DIV.appHeader(block: HeaderContext.() -> Unit = {}) {
    val headerContext = HeaderContext().apply(block)

    header(classes = "header") {
        div(classes = "container header-content") {
            // Logo
            a(href = "/", classes = "logo") {
                +"Yousef.Codes"
            }

            // Navigation links
            nav(classes = "nav") {
                div(classes = "nav-links") {
                    val currentPath = window.location.pathname

                    navLink("/", "Home", isActive = currentPath == "/")
                    navLink("/projects", "Projects", isActive = currentPath.startsWith("/projects"))
                    navLink("/blog", "Blog", isActive = currentPath.startsWith("/blog"))
                    navLink("/services", "Services", isActive = currentPath.startsWith("/services"))
                    navLink("/about", "About", isActive = currentPath == "/about")
                    navLink("/contact", "Contact", isActive = currentPath == "/contact")
                }
            }

            // Apply the click handler to all links
            onClickFunction = headerContext.onClickFunction
        }
    }
}

/**
 * Helper function to create a navigation link
 */
private fun DIV.navLink(href: String, text: String, isActive: Boolean = false) {
    a(href = href, classes = "nav-link${if (isActive) " active" else ""}") {
        +text
    }
}

/**
 * Header context class for configuring the header
 */
class HeaderContext {
    var onClickFunction: (dynamic) -> Unit = {}
}