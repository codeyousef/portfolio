package code.yousef.infrastructure.template

import jakarta.enterprise.context.ApplicationScoped
import kotlinx.css.*
import kotlinx.css.properties.*

@ApplicationScoped
class StylesheetGenerator {

    fun generateStyles(): String {
        return CssBuilder().apply {
            // Color variables
            rule(":root") {
                put("--deep-space-blue", "#0a0a14")
                put("--cyber-cyan", "#00f7ff")
                put("--neon-pink", "#ff2a6d")
                put("--bg-dark", "#020024")
                put("--bg-mid", "#090979")
            }

            // Global styles
            body {
                margin = Margin(0.px)
                padding = Padding(0.px)
                fontFamily = "'Space Grotesk', sans-serif"
                backgroundColor = Color("var(--deep-space-blue)")
                color = Color.white
                position = Position.relative
                overflow = Overflow.auto
                minHeight = 100.vh
            }

            // Canvas background
            rule("#bg-canvas") {
                position = Position.fixed
                top = 0.px
                left = 0.px
                width = 100.pct
                height = 100.pct
                zIndex = -1
                pointerEvents = PointerEvents.none
            }

            // Header styles
            header {
                padding = Padding(16.px)
                position = Position.relative
                zIndex = 10

                "h1" {
                    fontSize = 3.rem
                }
            }

            // Glowing Text
            rule(".glowing-text") {
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 12.px)
                }
            }

            nav {
                ul {
                    display = Display.flex
                    listStyleType = ListStyleType.none
                    padding = Padding(0.px)
                }
                li {
                    marginRight = 16.px
                }
                a {
                    color = Color.white
                    textDecoration = TextDecoration.none
                    transition = Transitions().apply {
                        this += Transition(property = "all", duration = 0.3.s, timing = Timing.ease)
                    }

                    hover {
                        color = Color("var(--cyber-cyan)")
                        textShadow = TextShadows().apply {
                            this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 8.px)
                        }
                    }
                }
            }

            // Glass container
            rule(".glass-container") {
                backdropFilter = "blur(12px)"
                backgroundColor = Color("rgba(10, 10, 20, 0.5)")
                borderRadius = 10.px
                padding = Padding(20.px)
                margin = Margin(20.px, LinearDimension.auto)
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.3)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 247, 255, 0.2)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 20.px)
                }
                overflow = Overflow.visible
                maxHeight = LinearDimension.none
                maxWidth = 1200.px
                width = 100.pct
            }

            // Glass morphic elements
            rule(".glass-morphic") {
                backdropFilter = "blur(12px)"
                backgroundColor = Color("rgba(10, 10, 20, 0.3)")
                borderRadius = 8.px
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.2)"))
                transition = Transitions().apply {
                    this += Transition(property = "all", duration = 0.4.s, timing = Timing.ease)
                }
            }

            // Projects styles
            rule(".projects-container") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(3, 1fr)")
                gap = 30.px
                padding = Padding(20.px)
                marginBottom = 60.px
                width = 100.pct
            }
            
            media("(max-width: 1024px)") {
                rule(".projects-container") {
                    gridTemplateColumns = GridTemplateColumns("repeat(2, 1fr)")
                }
            }
            
            media("(max-width: 768px)") {
                rule(".projects-container") {
                    gridTemplateColumns = GridTemplateColumns("1fr")
                }
            }

            rule(".project-card") {
                position = Position.relative
                height = 300.px
                transform {
                    perspective(1000.px)
                }
                minWidth = 250.px

                hover {
                    "& .card-inner" {
                        transform { rotateY(180.deg) }
                    }
                }
            }

            rule(".card-inner") {
                width = 100.pct
                height = 100.pct
                position = Position.relative
                transition = Transitions().apply {
                    this += Transition(property = "transform", duration = 0.4.s, timing = Timing.ease)
                }
                put("transform-style", "preserve-3d")
            }

            rule(".card-front, .card-back") {
                position = Position.absolute
                width = 100.pct
                height = 100.pct
                put("backface-visibility", "hidden")
                padding = Padding(20.px)
                borderRadius = 8.px
                backdropFilter = "blur(12px)"
                backgroundColor = Color("rgba(10, 10, 20, 0.5)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.3)"))
                boxSizing = BoxSizing.borderBox
                overflow = Overflow.hidden
            }

            rule(".card-front") {
                "h3" {
                    color = Color.white
                    textShadow = TextShadows().apply {
                        this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 12.px)
                    }
                    marginTop = 0.px
                    marginBottom = 10.px
                    fontSize = 1.4.rem
                }
                
                "p" {
                    fontSize = 0.9.rem
                    overflow = Overflow.hidden
                    textOverflow = TextOverflow.ellipsis
                    put("display", "-webkit-box")
                    put("-webkit-line-clamp", "3")
                    put("-webkit-box-orient", "vertical")
                    marginBottom = 10.px
                }
            }

            rule(".card-back") {
                transform { rotateY(180.deg) }
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.center
                alignItems = Align.center
            }

            // 3D model container
            rule(".model-container") {
                width = 100.pct
                height = 150.px
                margin = Margin(0.px, 0.px, 15.px, 0.px)
            }
            
            // Technology pills/tags styling
            rule(".tech-stack") {
                display = Display.flex
                flexWrap = FlexWrap.wrap
                gap = 8.px
                marginTop = 12.px
                overflowY = Overflow.auto
                maxHeight = 70.px
                paddingRight = 5.px
            }
            
            rule(".tech-pill") {
                display = Display.inlineBlock
                backgroundColor = Color("rgba(0, 247, 255, 0.15)")
                color = Color("var(--cyber-cyan)")
                padding = Padding(4.px, 10.px)
                borderRadius = 20.px
                fontSize = 0.8.rem
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.3)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 247, 255, 0.1)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)
                }
                transition = Transitions().apply {
                    this += Transition(property = "all", duration = 0.3.s, timing = Timing.ease)
                }
            }
            
            rule(".tech-pill:hover") {
                backgroundColor = Color("rgba(0, 247, 255, 0.25)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 247, 255, 0.3)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 8.px)
                }
            }
            
            // Project links styling
            rule(".project-links") {
                display = Display.flex
                gap = 10.px
                marginTop = 15.px
            }
            
            rule(".neon-btn") {
                display = Display.inlineBlock
                backgroundColor = Color("rgba(10, 10, 20, 0.7)")
                color = Color("var(--cyber-cyan)")
                padding = Padding(8.px, 16.px)
                borderRadius = 5.px
                textDecoration = TextDecoration.none
                border = Border(1.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
                textAlign = TextAlign.center
                transition = Transitions().apply {
                    this += Transition(property = "all", duration = 0.3.s, timing = Timing.ease)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 247, 255, 0.1)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)
                }
            }
            
            rule(".neon-btn:hover") {
                backgroundColor = Color("rgba(0, 247, 255, 0.2)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 247, 255, 0.4)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
                transform {
                    translateY(-2.px)
                }
            }
        }.toString()
    }
}