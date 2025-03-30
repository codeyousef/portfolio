package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class ProjectStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Projects grid
            rule(".projects-grid") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(auto-fill, minmax(350px, 1fr))")
                gap = LinearDimension("var(--spacing-lg)")
            }

            // Project card
            rule(".project-card") {
                position = Position.relative
                borderRadius = LinearDimension("var(--border-radius-lg)")
                overflow = Overflow.hidden
                transition = Transitions().apply {
                    this += Transition("transform", Time("var(--transition-medium)"))
                    this += Transition("box-shadow", Time("var(--transition-medium)"))
                }
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
                transform { scale(1.05) }
            }

            rule(".project-card:hover .project-content") {
                background =
                    "linear-gradient(to top, rgba(10, 10, 20, 0.9) 0%, rgba(10, 10, 20, 0.8) 50%, rgba(10, 10, 20, 0) 100%)"
            }

            // Project image
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
                transition = Transitions().apply {
                    this += Transition("transform", Time("var(--transition-slow)"))
                }
            }

            // Project content overlay
            rule(".project-content") {
                position = Position.absolute
                bottom = 0.px
                left = 0.px
                width = 100.pct
                padding = Padding(LinearDimension("var(--spacing-lg)"))
                background =
                    "linear-gradient(to top, rgba(10, 10, 20, 0.8) 0%, rgba(10, 10, 20, 0.6) 50%, rgba(10, 10, 20, 0) 100%)"
                zIndex = 1
                transition = Transitions().apply {
                    this += Transition("background", Time("var(--transition-medium)"))
                }
            }

            // Project title
            rule(".project-title") {
                fontFamily = "var(--font-heading)"
                fontSize = 1.5.rem
                fontWeight = FontWeight.w600
                marginBottom = LinearDimension("var(--spacing-sm)")
                color = Color("var(--primary)")
            }

            // Project description
            rule(".project-desc") {
                color = Color("var(--text-secondary)")
                marginBottom = LinearDimension("var(--spacing-md)")
                display = Display.block
                put("-webkit-line-clamp", "2")
                put("-webkit-box-orient", "vertical")
                overflow = Overflow.hidden
            }

            // Project tags
            rule(".project-tags") {
                display = Display.flex
                flexWrap = FlexWrap.wrap
                gap = LinearDimension("var(--spacing-xs)")
                marginBottom = LinearDimension("var(--spacing-md)")
            }

            rule(".project-tag") {
                fontSize = 0.8.rem
                padding = Padding(2.px, 8.px)
                borderRadius = LinearDimension("var(--border-radius-sm)")
                backgroundColor = Color("rgba(0, 247, 255, 0.1)")
                color = Color("var(--primary)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.2)"))
            }

            // Project links
            rule(".project-links") {
                display = Display.flex
                gap = LinearDimension("var(--spacing-sm)")
            }

            rule(".project-link") {
                fontSize = 0.9.rem
                padding = Padding(6.px, 12.px)
                borderRadius = LinearDimension("var(--border-radius-sm)")
                backgroundColor = Color("rgba(0, 0, 0, 0.3)")
                color = Color("var(--text-primary)")
                textDecoration = TextDecoration.none
                transition = Transitions().apply {
                    this += Transition("background-color", Time("var(--transition-fast)"))
                }
                border = Border(1.px, BorderStyle.solid, Color("rgba(255, 255, 255, 0.1)"))
            }

            rule(".project-link:hover") {
                backgroundColor = Color("var(--primary)")
                color = Color("var(--bg-dark)")
            }

            // Section styling
            rule(".section") {
                padding = Padding(LinearDimension("var(--spacing-xl)"))
            }

            rule(".section-header") {
                textAlign = TextAlign.center
                marginBottom = LinearDimension("var(--spacing-xl)")
            }

            rule(".section-title") {
                fontFamily = "var(--font-heading)"
                fontSize = 2.5.rem
                fontWeight = FontWeight.w700
                marginBottom = LinearDimension("var(--spacing-sm)")
                position = Position.relative
                display = Display.inlineBlock
            }

            rule(".section-title::after") {
                content = QuotedString("")
                position = Position.absolute
                bottom = (-10).px
                left = 50.pct
                transform { translateX((-50).pct) }
                width = 60.px
                height = 3.px
                background = "linear-gradient(90deg, var(--primary), var(--secondary))"
                borderRadius = LinearDimension("var(--border-radius-lg)")
            }

            rule(".section-desc") {
                maxWidth = 600.px
                margin = Margin(0.px, LinearDimension.auto)
                color = Color("var(--text-secondary)")
            }

            // Media queries for responsive design
            media("(max-width: 1024px)") {
                rule(".section-title") {
                    fontSize = 2.rem
                }
            }

            media("(max-width: 768px)") {
                rule(".section") {
                    padding = Padding(LinearDimension("var(--spacing-xl)"), LinearDimension("var(--spacing-md)"))
                }

                rule(".projects-grid") {
                    gridTemplateColumns = GridTemplateColumns("1fr")
                }
            }
        }
    }
}