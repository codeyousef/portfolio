package code.yousef.components

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

/**
 * App footer component
 */
fun DIV.appFooter() {
    footer(classes = "footer") {
        div(classes = "container footer-content") {
            // Copyright
            div(classes = "copyright") {
                +"© ${js("new Date().getFullYear()")} Yousef.Codes. All rights reserved."
            }

            // Social links
            div(classes = "social-links") {
                a(href = "https://github.com/yousef", target = "_blank") {
                    +"GitHub"
                }
                a(href = "https://linkedin.com/in/yousef", target = "_blank") {
                    +"LinkedIn"
                }
                a(href = "https://twitter.com/yousef", target = "_blank") {
                    +"Twitter"
                }
            }
        }
    }
}