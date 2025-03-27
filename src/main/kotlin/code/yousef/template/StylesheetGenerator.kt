package code.yousef.template

import jakarta.enterprise.context.ApplicationScoped
import kotlinx.css.*
import kotlinx.css.properties.*

@ApplicationScoped
class StylesheetGenerator {
    
    fun generateStyles(): String {
        return CssBuilder().apply {
            // Global styles
            body {
                margin= Margin(0.px)
                padding = Padding(0.px)
                fontFamily = "Arial, sans-serif"
            }
            
            // Header styles
            header {
                backgroundColor = Color.darkBlue
                color = Color.white
                padding = Padding(16.px)
            }
            
           nav {
               ul {
                   display = Display.flex
                   listStyleType = ListStyleType.none
                   padding = Padding(0.px)
               }
               li {
                   marginRight = 16.px
               }
               a{
                   color = Color.white
                   textDecoration = TextDecoration.none
               }
            }
            
            // Projects styles
            rule(".projects-container") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(auto-fill, minmax(300px, 1fr))")
                gap = 20.px
                padding = Padding(20.px)
            }
            
            rule(".project-card") {
                border = Border(1.px, BorderStyle.solid, Color("#ccc"))
                borderRadius = 5.px
                padding = Padding(16.px)
                cursor = Cursor.pointer
                transition = Transitions().apply { this += Transition(property = "all", duration = 0.3.s, timing = Timing.ease) }
                
                hover {
                    boxShadow = BoxShadows().apply { this += BoxShadow(color =Color("rgba(0, 0, 0, 0.2)"), offsetX = 0.px, offsetY = 3.px, blurRadius = 5.px) }
                    transform { scale(1.03) }
                }
            }
            
            // Add other styles as needed
        }.toString()
    }
}