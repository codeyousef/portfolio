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
                transition = Transitions().apply {
                    this += Transition("all", Time("var(--transition-medium)"))
                }
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
                transition = Transitions().apply {
                    this += Transition("color", Time("var(--transition-fast)"))
                }
            }

            rule(".nav-links a::after") {
                content = QuotedString("")
                position = Position.absolute
                bottom = (-4).px
                left = 0.px
                width = 0.px
                height = 2.px
                backgroundColor = Color("var(--primary)")
                transition = Transitions().apply {
                    this += Transition("width", Time("var(--transition-medium)"))
                }
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
                background = "transparent"
                border = Border.none
                cursor = Cursor.pointer
                width = 40.px
                height = 40.px
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                borderRadius = 50.pct
                backgroundColor = Color("var(--bg-surface)")
                transition = Transitions().apply {
                    this += Transition("background-color", Time("var(--transition-fast)"))
                }
            }

            rule(".theme-toggle:hover") {
                backgroundColor = Color("rgba(var(--primary-rgb), 0.1)")
            }

            rule(".theme-toggle svg") {
                width = 20.px
                height = 20.px
                put("fill", "var(--primary)")
                transition = Transitions().apply {
                    this += Transition("transform", Time("var(--transition-medium)"))
                }
            }

            rule(".theme-toggle:hover svg") {
                transform { rotate(30.deg) }
            }

            // CTA Button
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
                transform { translateY((-2).px) }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(Color("rgba(0, 247, 255, 0.3)"), 0.px, 5.px, 15.px)
                }
            }

            rule(".cta-button.secondary") {
                background = "linear-gradient(90deg, var(--secondary), var(--secondary-dark))"
                marginLeft = LinearDimension("var(--spacing-md)")
            }

            rule(".cta-button.secondary:hover") {
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(Color("rgba(255, 42, 109, 0.3)"), 0.px, 5.px, 15.px)
                }
            }

            // Contact container
            rule(".contact-container") {
                display = Display.flex
                gap = LinearDimension("var(--spacing-xl)")
                maxWidth = 1000.px
                margin = Margin(0.px, LinearDimension.auto)
                alignItems = Align.flexStart
            }

            rule(".contact-info") {
                flex = Flex(1)
            }

            rule(".contact-info h3") {
                fontFamily = "var(--font-heading)"
                fontSize = 1.5.rem
                marginBottom = LinearDimension("var(--spacing-md)")
                color = Color("var(--primary)")
            }

            rule(".contact-info p") {
                color = Color("var(--text-secondary)")
                marginBottom = LinearDimension("var(--spacing-lg)")
            }

            rule(".contact-item") {
                display = Display.flex
                alignItems = Align.center
                marginBottom = LinearDimension("var(--spacing-md)")
            }

            rule(".contact-icon") {
                width = 40.px
                height = 40.px
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                backgroundColor = Color("rgba(0, 247, 255, 0.1)")
                borderRadius = 50.pct
                marginRight = LinearDimension("var(--spacing-md)")
            }

            rule(".contact-icon svg") {
                width = 20.px
                height = 20.px
                put("fill", "var(--primary)")
            }

            rule(".contact-text") {
                color = Color("var(--text-secondary)")
            }

            rule(".contact-text a") {
                color = Color("var(--primary)")
                textDecoration = TextDecoration.none
                transition = Transitions().apply {
                    this += Transition("color", Time("var(--transition-fast)"))
                }
            }

            rule(".contact-text a:hover") {
                color = Color("var(--text-primary)")
            }

            // Form style
            rule(".contact-form") {
                flex = Flex(1)
                backgroundColor = Color("var(--bg-card)")
                borderRadius = LinearDimension("var(--border-radius-lg)")
                padding = Padding(LinearDimension("var(--spacing-lg)"))
                border = Border(1.px, BorderStyle.solid, Color("rgba(255, 255, 255, 0.05)"))
            }

            rule(".form-group") {
                marginBottom = LinearDimension("var(--spacing-md)")
            }

            rule(".form-label") {
                display = Display.block
                marginBottom = LinearDimension("var(--spacing-xs)")
                color = Color("var(--text-secondary)")
                fontSize = 0.9.rem
            }

            rule(".form-input, .form-textarea") {
                width = 100.pct
                padding = Padding(LinearDimension("var(--spacing-sm)"))
                backgroundColor = Color("rgba(0, 0, 0, 0.2)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(255, 255, 255, 0.1)"))
                borderRadius = LinearDimension("var(--border-radius-sm)")
                color = Color("var(--text-primary)")
                fontFamily = "var(--font-body)"
                transition = Transitions().apply {
                    this += Transition("border-color", Time("var(--transition-fast)"))
                }
            }

            rule(".form-input:focus, .form-textarea:focus") {
                outline = Outline.none
                borderColor = Color("var(--primary)")
            }

            rule(".form-textarea") {
                minHeight = 120.px
                resize = Resize.vertical
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
                transition = Transitions().apply {
                    this += Transition("color", Time("var(--transition-fast)"))
                }
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
                transition = Transitions().apply {
                    this += Transition("background-color", Time("var(--transition-fast)"))
                    this += Transition("transform", Time("var(--transition-fast)"))
                }
            }

            rule(".social-icon:hover") {
                backgroundColor = Color("var(--primary)")
                transform { translateY((-3).px) }
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

            // Code window components
            rule(".code-window") {
                position = Position.absolute
                width = 400.px
                backgroundColor = Color("rgba(10, 10, 25, 0.7)")
                backdropFilter = "blur(10px)"
                borderRadius = LinearDimension("var(--border-radius-md)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.2)"))
                padding = Padding(LinearDimension("var(--spacing-md)"))
                transform { rotate(5.deg) }
                top = 30.pct
                right = 15.pct
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(Color("rgba(0, 0, 0, 0.2)"), 0.px, 10.px, 30.px)
                }
            }

            rule(".code-window-header") {
                display = Display.flex
                alignItems = Align.center
                marginBottom = LinearDimension("var(--spacing-md)")
                borderBottom = Border(1.px, BorderStyle.solid, Color("rgba(255, 255, 255, 0.1)"))
                paddingBottom = LinearDimension("var(--spacing-sm)")
            }

            rule(".window-dot") {
                width = 10.px
                height = 10.px
                borderRadius = 50.pct
                backgroundColor = Color("var(--secondary)")
                marginRight = LinearDimension("var(--spacing-xs)")
            }

            rule(".window-dot:first-child") {
                backgroundColor = Color("#ff5f56")
            }

            rule(".window-dot:nth-child(2)") {
                backgroundColor = Color("#ffbd2e")
            }

            rule(".window-dot:nth-child(3)") {
                backgroundColor = Color("#27c93f")
            }

            rule(".code-window-title") {
                marginLeft = LinearDimension("var(--spacing-sm)")
                fontSize = 0.8.rem
                color = Color("var(--text-tertiary)")
            }

            rule(".code-content") {
                fontFamily = "monospace"
                fontSize = 0.9.rem
                lineHeight = LineHeight("1.5")
                color = Color("var(--text-secondary)")
            }

            rule(".code-line") {
                marginBottom = LinearDimension("var(--spacing-xs)")
            }

            rule(".code-keyword") {
                color = Color("var(--secondary)")
            }

            rule(".code-function") {
                color = Color("var(--primary)")
            }

            rule(".code-string") {
                color = Color("var(--accent-green)")
            }

            rule(".code-number") {
                color = Color("var(--accent-purple)")
            }
        }
    }
}