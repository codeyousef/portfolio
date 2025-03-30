package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class ProjectStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            rule(".project-image") {
                width = 100.pct
                height = 200.px
                overflow = Overflow.hidden
                position = Position.relative
            }

            rule(".project-image img") {
                width = 100.pct
                height = 100.pct
                objectFit = ObjectFit.cover
                transition += Transition("transform", 0.5.s, Timing.ease)

            }

            rule(".project-card:hover .project-image img") {
                transform { scale(1.05) }
            }

            rule(".tech-stack") {
                display = Display.flex
                flexWrap = FlexWrap.wrap
                gap = 8.px
                marginBottom = 16.px
            }

            rule(".tech-pill") {
                fontSize = 0.8.rem
                padding = Padding(4.px, 10.px)
                borderRadius = 4.px
                backgroundColor = Color("rgba(var(--primary-rgb), 0.1)")
                color = Color("var(--primary)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(var(--primary-rgb), 0.2)"))
            }

            rule(".project-links") {
                display = Display.flex
                gap = 15.px
                marginTop = 20.px
            }

            rule(".project-links a") {
                display = Display.inlineBlock
                padding = Padding(8.px, 16.px)
                borderRadius = 6.px
                textDecoration = TextDecoration.none
                color = Color("var(--primary)")
                border = Border(1.px, BorderStyle.solid, Color("var(--primary)"))
                transition += Transition("all", 0.3.s, Timing.ease)
            }

            rule(".project-links a:hover") {
                backgroundColor = Color("var(--primary)")
                color = Color("var(--bg-dark)")
            }
        }
    }
}