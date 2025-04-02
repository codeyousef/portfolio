package code.yousef

import code.yousef.components.*
import code.yousef.routes.Router
import code.yousef.styles.AppStyles
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.dom.append
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.get

fun main() {
    window.onload = {
        // Initialize styles
        AppStyles.init()

        // If server-side rendered content exists, remove it
        document.getElementById("seo-content")?.let { seoContent ->
            // We don't immediately remove the SSR content to avoid flicker
            // It will be replaced by the client-side rendering
        }

        // Get the root element
        val root = document.getElementById("root") ?: document.body!!

        // Clear existing content if any
        root.innerHTML = ""

        // Initialize the router
        val router = Router()

        // Append the app shell
        root.append {
            div {
                id = "app"

                // Render the app header
                appHeader {
                    // Router navigation handler for links
                    onClickFunction = { event ->
                        val target = event.target
                        if (target is HTMLElement && target.tagName.toLowerCase() == "a") {
                            event.preventDefault()
                            val href = target.getAttribute("href")
                            if (href != null) {
                                router.navigateTo(href)
                            }
                        }
                    }
                }

                // Main content area that will be managed by the router
                div {
                    id = "content"
                }

                // Render the app footer
                appFooter()
            }
        }

        // Initialize router with the content element
        router.initialize(document.getElementById("content")!!)
    }
}