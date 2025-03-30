package code.yousef.infrastructure.template.styles

import kotlinx.css.*

class MediaQueryStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Mobile styles
            media("(max-width: 768px)") {
                rule(".projects-grid") {
                    gridTemplateColumns = GridTemplateColumns("1fr")
                }

                rule(".hero h1, .hero h2") {
                    fontSize = 2.5.rem
                }
            }
        }
    }
}