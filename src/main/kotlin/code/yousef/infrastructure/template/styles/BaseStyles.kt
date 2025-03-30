package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.css.properties.Time
import kotlinx.css.properties.Transition
import kotlinx.css.properties.Transitions

class BaseStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Root variables
            rule(":root") {
                // Primary Colors
                put("--primary", "#00f7ff")
                put("--primary-dark", "#00c3c9")
                put("--primary-rgb", "0, 247, 255")
                put("--secondary", "#ff2a6d")
                put("--secondary-dark", "#d31557")
                put("--secondary-rgb", "255, 42, 109")

                // Background Colors
                put("--bg-dark", "#0a0a14")
                put("--bg-card", "rgba(16, 16, 30, 0.7)")
                put("--bg-surface", "rgba(20, 20, 35, 0.5)")

                // Text Colors
                put("--text-primary", "#ffffff")
                put("--text-secondary", "rgba(255, 255, 255, 0.7)")
                put("--text-tertiary", "rgba(255, 255, 255, 0.5)")

                // Accent Colors
                put("--accent-green", "#05ffa1")
                put("--accent-green-rgb", "5, 255, 161")
                put("--accent-purple", "#b967ff")
                put("--accent-purple-rgb", "185, 103, 255")
                put("--accent-blue", "#0162ff")
                put("--accent-blue-rgb", "1, 98, 255")

                // Spacing
                put("--spacing-xs", "0.25rem")
                put("--spacing-sm", "0.5rem")
                put("--spacing-md", "1rem")
                put("--spacing-lg", "2rem")
                put("--spacing-xl", "4rem")

                // Borders
                put("--border-radius-sm", "0.25rem")
                put("--border-radius-md", "0.5rem")
                put("--border-radius-lg", "1rem")

                // Typography
                put("--font-heading", "'Syne', sans-serif")
                put("--font-body", "'Space Grotesk', sans-serif")

                // Animations
                put("--transition-fast", "0.2s ease")
                put("--transition-medium", "0.3s ease")
                put("--transition-slow", "0.5s ease")
            }

            // Light theme overrides
            rule("[data-theme='light']") {
                put("--primary", "#0084ff")
                put("--primary-dark", "#0065c3")
                put("--secondary", "#e81556")
                put("--secondary-dark", "#b41044")

                put("--bg-dark", "#f0f2f5")
                put("--bg-card", "rgba(255, 255, 255, 0.7)")
                put("--bg-surface", "rgba(255, 255, 255, 0.5)")

                put("--text-primary", "#121212")
                put("--text-secondary", "rgba(18, 18, 18, 0.7)")
                put("--text-tertiary", "rgba(18, 18, 18, 0.5)")
            }

            // Reset & base styles
            universal {
                margin = Margin(0.px)
                padding = Padding(0.px)
                boxSizing = BoxSizing.borderBox
            }

            html {
                scrollBehavior = ScrollBehavior.smooth
                scrollPaddingTop = 80.px
            }

            body {
                fontFamily = "var(--font-body)"
                backgroundColor = Color("var(--bg-dark)")
                color = Color("var(--text-primary)")
                lineHeight = LineHeight("1.6")
                overflowX = Overflow.hidden
                transition = Transitions().apply {
                    this += Transition("background-color", Time("var(--transition-medium)"))
                }
            }

            // Background effect
            rule("body::before") {
                content = QuotedString("")
                position = Position.fixed
                top = 0.px
                left = 0.px
                width = 100.pct
                height = 100.pct
                background =
                    "radial-gradient(circle at top right, rgba(1, 98, 255, 0.15), transparent 60%), radial-gradient(circle at bottom left, rgba(255, 42, 109, 0.15), transparent 60%)"
                zIndex = (-1)
                pointerEvents = PointerEvents.none
            }

            // Grid lines
            rule(".grid-lines") {
                position = Position.fixed
                top = 0.px
                left = 0.px
                width = 100.pct
                height = 100.pct
                backgroundSize = "50px 50px"
                backgroundImage =
                    Image("linear-gradient(to right, rgba(0, 247, 255, 0.03) 1px, transparent 1px), linear-gradient(to bottom, rgba(0, 247, 255, 0.03) 1px, transparent 1px)")
                zIndex = (-1)
                pointerEvents = PointerEvents.none
            }
        }
    }
}