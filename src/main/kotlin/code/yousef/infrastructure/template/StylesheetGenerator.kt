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
                overflow = Overflow.hidden
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
                margin = Margin(20.px)
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.3)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 247, 255, 0.2)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 20.px)
                }
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
                gridTemplateColumns = GridTemplateColumns("repeat(auto-fill, minmax(300px, 1fr))")
                gap = 20.px
                padding = Padding(20.px)
            }

            rule(".project-card") {
                position = Position.relative
                height = 300.px
                transform {
                    perspective(1000.px)
                }

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
            }

            rule(".card-front") {
                "h3" {
                    color = Color.white
                    textShadow = TextShadows().apply {
                        this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 12.px)
                    }
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
        }.toString()
    }
}