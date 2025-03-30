package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class CodeWindowStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
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
                fontFamily = "monospace, 'Space Mono', Consolas"
                fontSize = 0.9.rem
                lineHeight = LineHeight("1.5")
                color = Color("var(--text-secondary)")
            }

            rule(".code-line") {
                marginBottom = LinearDimension("var(--spacing-xs)")
                whiteSpace = WhiteSpace.pre
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