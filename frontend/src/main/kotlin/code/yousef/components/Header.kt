package code.yousef.components

import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.header
import kotlinx.html.h2
import kotlinx.html.nav
import kotlinx.html.a
import kotlinx.html.style

fun FlowContent.header() {
    header {
        style = "background-color: #333; color: white; padding: 1rem 0; display: flex; justify-content: space-between; align-items: center; padding: 0 2rem;"
        
        div {
            style = "font-size: 1.5rem; font-weight: bold;"
            h2 {
                +"Portfolio"
            }
        }
        
        nav {
            style = "display: flex; gap: 1.5rem;"
            a {
                style = "color: white; text-decoration: none;"
                href = "/"
                +"Home"
            }
            a {
                style = "color: white; text-decoration: none;"
                href = "/projects"
                +"Projects"
            }
            a {
                style = "color: white; text-decoration: none;"
                href = "/about"
                +"About"
            }
            a {
                style = "color: white; text-decoration: none;"
                href = "/contact"
                +"Contact"
            }
        }
    }
} 