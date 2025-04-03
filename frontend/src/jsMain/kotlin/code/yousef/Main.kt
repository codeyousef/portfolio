package code.yousef

import code.yousef.components.appFooter
import code.yousef.components.appHeader
import code.yousef.routes.Router
import code.yousef.styles.PortfolioStyles
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import org.w3c.dom.Element
import org.w3c.dom.events.Event

fun main() {
    window.onload = {
        // Initialize styles
        PortfolioStyles.init()

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
                    onClickFunction = { event: Event ->
                        val target = event.target
                        if (target is Element && target.tagName.toLowerCase() == "a") {
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

private fun PortfolioStyles.init() {
    TODO("Not yet implemented")
}
