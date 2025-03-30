package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class ProjectStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            rule(".projects-grid") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(auto-fill, minmax(350px, 1fr))")
                gap = LinearDimension("var(--spacing-lg)")
            }

            rule(".project-title") {
                fontFamily = "var(--font-heading)"
                fontSize = 1.5.rem
                fontWeight = FontWeight.w600
                marginBottom = LinearDimension("var(--spacing-sm)")
                color = Color("var(--primary)")
            }
            
            rule(".project-desc") {
                color = Color("var(--text-secondary)")
                marginBottom = LinearDimension("var(--spacing-md)")
                put("display", "-webkit-box")
                put("-webkit-line-clamp", "2")
                put("-webkit-box-orient", "vertical")
                overflow = Overflow.hidden
            }
            
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
                transition = Transitions()
                transition += Transition("background-color", Time("var(--transition-fast)"))
                border = Border(1.px, BorderStyle.solid, Color("rgba(255, 255, 255, 0.1)"))
            }
            
            rule(".project-link:hover") {
                backgroundColor = Color("var(--primary)")
                color = Color("var(--bg-dark)")
            }
            
            // Hero section
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
                backgroundClip = BackgroundClip.text
                color = Color.transparent
            }
            
            rule(".hero p") {
                fontSize = 1.2.rem
                color = Color("var(--text-secondary)")
                marginBottom = LinearDimension("var(--spacing-lg)")
                maxWidth = 600.px
            }
            
            // CTA buttons
            rule(".cta-button") {
                display = Display.inlineBlock
                padding = Padding(LinearDimension("var(--spacing-sm)"), LinearDimension("var(--spacing-lg)"))
                background = "linear-gradient(90deg, var(--primary), var(--primary-dark))"
                color = Color("var(--bg-dark)")
                fontWeight = FontWeight.w600
                textDecoration = TextDecoration.none
                borderRadius = LinearDimension("var(--border-radius-md)")
                transition = Transitions().apply {
                    this += Transition("transform", Time("var(--transition-fast)"))
                    this += Transition("box-shadow", Time("var(--transition-fast)"))
                }
                border = Border.none
                cursor = Cursor.pointer
                fontFamily = "var(--font-body)"
                fontSize = 1.rem
            }
            
            rule(".cta-button:hover") {
                transform {
                    translateY((-2).px)
                }
                boxShadow += BoxShadow(offsetX = 0.px, offsetY = 5.px, blurRadius = 15.px, color = Color("rgba(0, 247, 255, 0.3)"))
            }
            
            rule(".cta-button.secondary") {
                background = "linear-gradient(90deg, var(--secondary), var(--secondary-dark))"
                marginLeft = LinearDimension("var(--spacing-md)")
            }
            
            rule(".cta-button.secondary:hover") {
                boxShadow += BoxShadow(offsetX = 0.px, offsetY = 5.px, blurRadius = 15.px, color = Color("rgba(255, 42, 109, 0.3)"))
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
        }
    }
}