package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class ComponentStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Project cards
            rule(".project-card") {
                position = Position.relative
                borderRadius = 12.px
                overflow = Overflow.hidden
                transition += Transition("transform", 0.4.s)
                transition += Transition("box-shadow", 0.4.s)
                height = 400.px
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.2)"))
            }

            rule(".project-card:hover") {
                transform { translateY((-10).px) }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 0, 0, 0.2)"), offsetY = 15.px, blurRadius = 30.px)
                }
            }

            // Card content
            rule(".project-content") {
                position = Position.absolute
                bottom = 0.px
                left = 0.px
                width = 100.pct
                padding = Padding(20.px)
                background = "linear-gradient(to top, rgba(10, 10, 20, 0.8), transparent)"
                zIndex = 1
            }
        }
    }
}