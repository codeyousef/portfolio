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
                padding = Padding(60.px, 40.px)
            }
        }
    }
}