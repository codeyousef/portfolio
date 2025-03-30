package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.BoxShadows
import kotlinx.css.properties.Transforms

class ThemeStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Skills styling
            rule(".tech-circuit") {
                position = Position.relative
                maxWidth = 1100.px
                margin = Margin(0.px, LinearDimension.auto)
            }

            // No effects mode
            rule(".no-effects .project-card:hover") {
                transform = Transforms()
                boxShadow = BoxShadows()
            }
        }
    }
}