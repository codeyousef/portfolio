package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class TerminalStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Terminal Element - Complete styling
            rule(".terminal") {
                position = Position.absolute
                width = 350.px
                backgroundColor = Color("rgba(10, 10, 25, 0.8)")
                backdropFilter = "blur(10px)"
                put("-webkit-backdrop-filter", "blur(10px)")
                borderRadius = LinearDimension("var(--border-radius-md)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.2)"))
                padding = Padding(LinearDimension("var(--spacing-md)"))
                top = 60.pct
                right = 25.pct
                transform { rotate((-8).deg) }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(Color("rgba(0, 0, 0, 0.2)"), 0.px, 10.px, 30.px)
                }
                zIndex = 1
            }

            rule(".terminal.pulse") {
                put("animation", "pulse 2s ease-in-out infinite")
            }

            rule(".terminal-header") {
                display = Display.flex
                alignItems = Align.center
                marginBottom = LinearDimension("var(--spacing-md)")
                borderBottom = Border(1.px, BorderStyle.solid, Color("rgba(255, 255, 255, 0.1)"))
                paddingBottom = LinearDimension("var(--spacing-sm)")
            }

            rule(".terminal-title") {
                fontFamily = "monospace, 'Space Mono', Consolas"
                color = Color("var(--text-tertiary)")
                fontSize = 0.8.rem
            }

            rule(".terminal-content") {
                fontFamily = "monospace, 'Space Mono', Consolas"
                fontSize = 0.9.rem
                lineHeight = LineHeight("1.5")
                color = Color("var(--text-secondary)")
            }

            rule(".prompt") {
                color = Color("var(--primary)")
                marginRight = LinearDimension("var(--spacing-xs)")
            }

            rule(".command") {
                color = Color("var(--text-primary)")
            }

            rule(".output") {
                color = Color("var(--accent-green)")
                marginTop = LinearDimension("var(--spacing-xs)")
                marginBottom = LinearDimension("var(--spacing-sm)")
                whiteSpace = WhiteSpace.preWrap
            }

            // Code window elements
            rule(".code-window") {
                position = Position.absolute
                width = 400.px
                backgroundColor = Color("rgba(10, 10, 25, 0.7)")
                backdropFilter = "blur(10px)"
                put("-webkit-backdrop-filter", "blur(10px)")
                borderRadius = LinearDimension("var(--border-radius-md)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 247, 255, 0.2)"))
                padding = Padding(LinearDimension("var(--spacing-md)"))
                transform { rotate(5.deg) }
                top = 30.pct
                right = 15.pct
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 0, 0, 0.2)"), offsetY = 10.px, blurRadius = 30.px)
                }
                zIndex = 1
            }

            rule(".code-window.floating") {
                put("animation", "floating 3s ease-in-out infinite")
            }

            // Animation keyframes for floating and pulse
            keyframes("floating") {
                from {
                    transform {
                        translateY(0.px)
                        rotate(5.deg)
                    }
                }
                to {
                    transform {
                        translateY((-10).px)
                        rotate(5.deg)
                    }
                }
            }

            keyframes("pulse") {
                from {
                    opacity = 1
                }
                to {
                    opacity = 0.7
                }
            }
        }
    }
}