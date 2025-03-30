package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class LayoutStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Containers
            rule(".projects-container") {
                width = 100.pct
                maxWidth = 1200.px
                margin = Margin(0.px, LinearDimension.auto)
            }

            // Fix for the projects grid issue
            rule(".projects-grid") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(auto-fit, minmax(350px, 1fr))")
                gap = 40.px
                width = 100.pct
            }

            // Sections
            rule(".section") {
                padding = Padding(LinearDimension("var(--spacing-xl)"), LinearDimension("var(--spacing-xl)"))
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
                transform {
                    translateX((-50).pct)
                }
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

            // Hero Section
            rule(".hero") {
                minHeight = 100.vh
                display = Display.flex
                alignItems = Align.center
                padding = Padding(LinearDimension("var(--spacing-xl)"), LinearDimension("var(--spacing-xl)"))
                position = Position.relative
                overflow = Overflow.hidden
            }

            rule(".hero-content") {
                maxWidth = 700.px
                zIndex = 1
            }

            rule(".hero h1") {
                fontFamily = "var(--font-heading)"
                fontSize = 4.rem
                fontWeight = FontWeight.w700
                marginBottom = LinearDimension("var(--spacing-md)")
                lineHeight = LineHeight("1.1")
                background = "linear-gradient(90deg, var(--primary), var(--secondary))"
                put("-webkit-background-clip", "text")
                put("background-clip", "text")
                color = Color.transparent
            }

            rule(".hero p") {
                fontSize = 1.2.rem
                color = Color("var(--text-secondary)")
                marginBottom = LinearDimension("var(--spacing-lg)")
                maxWidth = 600.px
            }

            // Decorative elements
            rule(".hero-glow") {
                position = Position.absolute
                top = 20.pct
                right = 10.pct
                width = 600.px
                height = 600.px
                background = "radial-gradient(circle, rgba(0, 247, 255, 0.1) 0%, transparent 70%)"
                borderRadius = 50.pct
                filter = "blur(50px)"
                zIndex = 0
                pointerEvents = PointerEvents.none
            }

            rule(".hero-glow-2") {
                position = Position.absolute
                bottom = 10.pct
                left = 5.pct
                width = 400.px
                height = 400.px
                background = "radial-gradient(circle, rgba(255, 42, 109, 0.1) 0%, transparent 70%)"
                borderRadius = 50.pct
                filter = "blur(50px)"
                zIndex = 0
                pointerEvents = PointerEvents.none
            }

            rule(".floating-elements") {
                position = Position.absolute
                top = 0.px
                right = 0.px
                width = 50.pct
                height = 100.pct
                zIndex = 0
                overflow = Overflow.hidden
                opacity = 0.7
                pointerEvents = PointerEvents.none
            }
        }
    }
}