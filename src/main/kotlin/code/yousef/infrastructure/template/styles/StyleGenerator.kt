package code.yousef.infrastructure.template.styles

import kotlinx.css.CssBuilder

interface StyleGenerator {
    fun generateStyles(cssBuilder: CssBuilder)
}