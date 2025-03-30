package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class ComponentStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Navigation
            rule(".navbar") {
                position = Position.fixed
                top = 0.px
                left = 0.px
                width = 100.pct
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                padding = Padding(LinearDimension("var(--spacing-md)"), LinearDimension("var(--spacing-xl)"))
                background = "rgba(10, 10, 20, 0.8)"
                backdropFilter = "blur(10px)"
                zIndex = 1000
                borderBottom = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.1)"))
                transition += Transition("all", Time("var(--transition-medium)"))
            }

            rule("[data-theme=\"light\"] .navbar") {
                background = "rgba(240, 242, 245, 0.8)"
                borderBottom = Border(1.px, BorderStyle.solid, Color("rgba(1, 98, 255, 0.1)"))
            }

            rule(".logo") {
                fontFamily = "var(--font-heading)"
                fontWeight = FontWeight.w700
                fontSize = 1.5.rem
                color = Color("var(--primary)")
                textDecoration = TextDecoration.none
                display = Display.flex
                alignItems = Align.center
                gap = LinearDimension("var(--spacing-sm)")
            }

            rule(".logo span") {
                display = Display.inlineBlock
                width = 10.px
                height = 10.px
                backgroundColor = Color("var(--secondary)")
                borderRadius = 50.pct
            }

            rule(".nav-links") {
                display = Display.flex
                gap = LinearDimension("var(--spacing-lg)")
                listStyleType = ListStyleType.none
            }

            rule(".nav-links a") {
                color = Color("var(--text-primary)")
                textDecoration = TextDecoration.none
                fontWeight = FontWeight.w500
                position = Position.relative
                padding = Padding(LinearDimension("var(--spacing-xs)"), LinearDimension("var(--spacing-sm)"))
                transition += Transition("color", Time("var(--transition-fast)"))
            }

            rule(".nav-links a::after") {
                content = QuotedString("")
                position = Position.absolute
                bottom = (-4).px
                left = 0.px
                width = 0.px
                height = 2.px
                backgroundColor = Color("var(--primary)")
                transition += Transition("width", Time("var(--transition-medium)"))
            }

            rule(".nav-links a:hover") {
                color = Color("var(--primary)")
            }

            rule(".nav-links a:hover::after") {
                width = 100.pct
            }

            rule(".actions") {
                display = Display.flex
                alignItems = Align.center
                gap = LinearDimension("var(--spacing-md)")
            }

            // Theme toggle button
            rule(".theme-toggle") {
                background = "none"
                border = Border.none
                cursor = Cursor.pointer
                width = 40.px
                height = 40.px
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                borderRadius = 50.pct
                backgroundColor = Color("var(--bg-surface)")
                transition += (Transition("background-color", Time("var(--transition-fast)")))
            }

            rule(".theme-toggle:hover") {
                backgroundColor = Color("rgba(var(--primary-rgb), 0.1)")
            }

            rule(".theme-toggle svg") {
                width = 20.px
                height = 20.px
                put("fill", "var(--primary)")
                transition += (Transition("transform", Time("var(--transition-medium)")))
            }

            rule(".theme-toggle:hover svg") {
                transform {
                    rotate(30.deg)
                }
            }

            // Project cards
            rule(".project-card") {
                position = Position.relative
                borderRadius = LinearDimension("var(--border-radius-lg)")
                overflow = Overflow.hidden
                transition += Transition("transform", Time("var(--transition-medium)"))
                transition += Transition("box-shadow", Time("var(--transition-medium)"))
                height = 400.px
                cursor = Cursor.pointer
            }

            rule(".project-card:hover") {
                transform { translateY((-10).px) }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 0, 0, 0.2)"), offsetY = 15.px, blurRadius = 30.px)
                }
            }

            rule(".project-card:hover .project-image img") {
                transform {
                    scale(1.05)
                }
            }

            rule(".project-card:hover .project-content") {
                background =
                    "linear-gradient(to top, rgba(10, 10, 20, 0.9) 0%, rgba(10, 10, 20, 0.8) 50%, rgba(10, 10, 20, 0) 100%)"
            }

            rule(".project-image") {
                position = Position.absolute
                top = 0.px
                left = 0.px
                width = 100.pct
                height = 100.pct
                zIndex = 0
            }

            rule(".project-image img") {
                width = 100.pct
                height = 100.pct
                objectFit = ObjectFit.cover
                transition += Transition("transform", Time("var(--transition-slow)"))
            }

            // Card content
            rule(".project-content") {
                position = Position.absolute
                bottom = 0.px
                left = 0.px
                width = 100.pct
                padding = Padding(LinearDimension("var(--spacing-lg)"))
                background =
                    "linear-gradient(to top, rgba(10, 10, 20, 0.8) 0%, rgba(10, 10, 20, 0.6) 50%, rgba(10, 10, 20, 0) 100%)"
                zIndex = 1
                transition += Transition("background", Time("var(--transition-medium)"))
            }

            // Footer
            rule(".footer") {
                backgroundColor = Color("rgba(10, 10, 20, 0.9)")
                backdropFilter = "blur(10px)"
                padding = Padding(LinearDimension("var(--spacing-lg)"), LinearDimension("var(--spacing-xl)"))
                textAlign = TextAlign.center
                marginTop = LinearDimension("var(--spacing-xl)")
                borderTop = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.1)"))
            }

            rule(".footer-content") {
                maxWidth = 1200.px
                margin = Margin(0.px, LinearDimension.auto)
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
            }

            rule(".footer-logo") {
                fontFamily = "var(--font-heading)"
                fontWeight = FontWeight.w700
                fontSize = 1.5.rem
                color = Color("var(--primary)")
                textDecoration = TextDecoration.none
            }

            rule(".footer-links") {
                display = Display.flex
                gap = LinearDimension("var(--spacing-lg)")
            }

            rule(".footer-link") {
                color = Color("var(--text-secondary)")
                textDecoration = TextDecoration.none
                transition += Transition("color", Time("var(--transition-fast)"))
            }

            rule(".footer-link:hover") {
                color = Color("var(--primary)")
            }

            rule(".footer-social") {
                display = Display.flex
                gap = LinearDimension("var(--spacing-md)")
            }

            rule(".social-icon") {
                width = 40.px
                height = 40.px
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                backgroundColor = Color("rgba(255, 255, 255, 0.05)")
                borderRadius = 50.pct
                transition += Transition("background-color", Time("var(--transition-fast)"))
                transition += Transition("transform", Time("var(--transition-fast)"))
            }

            rule(".social-icon:hover") {
                backgroundColor = Color("var(--primary)")
                transform {
                    translateY((-3).px)
                }
            }

            rule(".social-icon svg") {
                width = 20.px
                height = 20.px
                put("fill", "var(--text-primary)")
            }

            rule(".copyright") {
                marginTop = LinearDimension("var(--spacing-lg)")
                color = Color("var(--text-tertiary)")
                fontSize = 0.9.rem
            }
        }
    }
}