package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class TerminalStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            rule(".terminal-container") {
                width = 100.pct
                maxWidth = 800.px
                margin = Margin(2.rem, LinearDimension.auto)
                backgroundColor = Color("rgba(0, 0, 0, 0.8)")
                borderRadius = 10.px
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 255, 255, 0.2)"), offsetY = 0.px, blurRadius = 20.px)
                }
                overflow = Overflow.hidden
            }

            rule(".terminal") {
                fontFamily = "'Space Mono', monospace"
                color = Color("#00ff00")
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("rgba(0, 255, 0, 0.5)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)
                }
            }

            rule(".terminal-header") {
                backgroundColor = Color("rgba(10, 10, 20, 0.7)")
                padding = Padding(0.5.rem, 1.rem)
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                borderBottom = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.2)"))
            }

            rule(".terminal-content") {
                padding = Padding(1.rem)
                minHeight = 200.px
                maxHeight = 400.px
                overflowY = Overflow.auto
            }

            rule(".terminal-input") {
                background = "transparent"
                border = Border.none
                color = Color("var(--primary)")
                fontFamily = "'Space Mono', monospace"
                fontSize = 1.rem
                outline = Outline.none
            }
        }
    }
}