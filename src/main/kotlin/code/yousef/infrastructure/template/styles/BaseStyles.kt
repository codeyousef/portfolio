package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class BaseStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Root variables
            rule(":root") {
                // Colors
                put("--primary", "#00f7ff")
                put("--primary-rgb", "0, 247, 255")
                put("--secondary", "#ff2a6d")
                put("--secondary-rgb", "255, 42, 109")
                // Background colors
                put("--bg-dark", "#0a0a14")
                put("--bg-surface", "rgba(20, 20, 35, 0.5)")
                // Text colors
                put("--text-primary", "#ffffff")
                put("--text-secondary", "#cccccc")
                // Typography
                put("--font-heading", "'Syne', sans-serif")
                put("--font-body", "'Space Grotesk', sans-serif")
                put("--font-mono", "'Space Mono', monospace")
            }

            // Light theme overrides
            rule("[data-theme='light']") {
                put("--primary", "#0084ff")
                put("--bg-dark", "#f0f2f5")
                put("--text-primary", "#121212")
            }

            // Reset & base styles
            universal {
                margin = Margin(0.px)
                padding = Padding(0.px)
                boxSizing = BoxSizing.borderBox
            }

            html {
                scrollBehavior = ScrollBehavior.smooth
            }

            body {
                fontFamily = "var(--font-body)"
                backgroundColor = Color("var(--bg-dark)")
                color = Color("var(--text-primary)")
                lineHeight = LineHeight("1.6")
                overflowX = Overflow.hidden
            }
        }
    }
}