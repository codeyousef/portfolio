package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class StylesheetGenerator {
    private val styleGenerators = listOf(
        BaseStyles(),
        LayoutStyles(),
        ComponentStyles(),
        ProjectStyles(),
        TerminalStyles(),
        AnimationStyles(),
        ThemeStyles(),
        MediaQueryStyles()
    )

    fun generateStyles(): String {
        return CssBuilder().apply {
            styleGenerators.forEach { generator ->
                generator.generateStyles(this)
            }
        }.toString()
    }
}