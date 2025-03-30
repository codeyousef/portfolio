package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class SkillStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            rule(".tech-circuit") {
                position = Position.relative
                maxWidth = 1100.px
                margin = Margin(0.px, LinearDimension.auto)
                padding = Padding(30.px, 0.px)
                minHeight = 500.px
            }

            rule(".circuit-line") {
                position = Position.absolute
                background =
                    "linear-gradient(90deg, rgba(var(--primary-rgb), 0.1), rgba(var(--primary-rgb), 0.6), rgba(var(--primary-rgb), 0.1))"
                height = 1.px
                width = 100.pct
                top = 50.pct
                left = 0.px
                put("transform-origin", "center")
                put("animation", "pulseLine 3s infinite alternate ease-in-out")
            }

            rule(".circuit-line") {
                position = Position.absolute
                background =
                    "linear-gradient(90deg, rgba(var(--primary-rgb), 0.1), rgba(var(--primary-rgb), 0.6), rgba(var(--primary-rgb), 0.1))"
                height = 1.px
                width = 100.pct
                top = 50.pct
                left = 0.px
                put("transform-origin", "center")
                put("animation", "pulseLine 3s infinite alternate ease-in-out")
            }

            rule(".circuit-line:nth-child(2)") {
                top = 35.pct
                put("animation-delay", "0.5s")
                put("animation-duration", "4s")
            }

            rule(".circuit-line:nth-child(3)") {
                top = 65.pct
                put("animation-delay", "1s")
                put("animation-duration", "3.5s")
            }

            rule(".circuit-line:nth-child(4)") {
                transform { rotate(45.deg) }
                put("transform-origin", "top left")
                width = 150.pct
                put("animation-delay", "1.5s")
            }

            rule(".circuit-line:nth-child(5)") {
                transform { rotate((-45).deg) }
                put("transform-origin", "top right")
                width = 150.pct
                put("animation-delay", "2s")
            }

            rule(".circuit-node") {
                position = Position.absolute
                width = 8.px
                height = 8.px
                borderRadius = 50.pct
                backgroundColor = Color("var(--primary)")
                put("box-shadow", "0 0 15px rgba(var(--primary-rgb), 0.8)")
                put("animation", "nodePulse 2s infinite alternate ease-in-out")
            }

            // Skill cards container
            rule(".skill-cards-container") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(auto-fit, minmax(250px, 1fr))")
                gap = 30.px
                position = Position.relative
                zIndex = 2
            }

            // Skill card with 3D effect
            rule(".skill-card") {
                put("perspective", "1000px")
                height = 280.px
            }

            rule(".skill-card-inner") {
                position = Position.relative
                width = 100.pct
                height = 100.pct
                textAlign = TextAlign.center
                put("transition", "transform 0.8s")
                put("transform-style", "preserve-3d")
                borderRadius = LinearDimension("var(--border-radius-lg)")
            }

            rule(".skill-card:hover .skill-card-inner") {
                transform { rotateY(180.deg) }
            }

            // Card faces
            rule(".skill-card-front, .skill-card-back") {
                position = Position.absolute
                width = 100.pct
                height = 100.pct
                put("-webkit-backface-visibility", "hidden")
                put("backface-visibility", "hidden")
                borderRadius = LinearDimension("var(--border-radius-lg)")
                display = Display.flex
                flexDirection = FlexDirection.column
                alignItems = Align.center
                justifyContent = JustifyContent.center
                padding = Padding(20.px)
                background = "rgba(16, 16, 30, 0.7)"
                background = "linear-gradient(135deg, rgba(16, 16, 30, 0.7) 0%, rgba(30, 30, 50, 0.7) 100%)"
                put("backdrop-filter", "blur(10px)")
                put("-webkit-backdrop-filter", "blur(10px)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(var(--primary-rgb), 0.2)"))
                put("box-shadow", "0 10px 25px rgba(0, 0, 0, 0.2), inset 0 0 40px rgba(var(--primary-rgb), 0.1)")
                overflow = Overflow.hidden
            }


            rule(".skill-card-front") {
                zIndex = 2
            }

            rule(".skill-card-back") {
                transform { rotateY(180.deg) }
                zIndex = 1
            }

            // Skill logo
            rule(".skill-logo") {
                width = 100.px
                height = 100.px
                put("object-fit", "contain")
                marginBottom = 20.px
                filter = "drop-shadow(0 0 10px rgba(var(--primary-rgb), 0.7))"
                put("transition", "all 0.3s ease")
            }

            rule(".skill-card:hover .skill-logo") {
                filter = "drop-shadow(0 0 15px rgba(var(--primary-rgb), 1))"
            }

            // Skill title and subtitle
            rule(".skill-title") {
                fontFamily = "var(--font-heading)"
                fontSize = 1.5.rem
                fontWeight = FontWeight.w600
                marginBottom = 10.px
                color = Color("var(--primary)")
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("rgba(var(--primary-rgb), 0.5)"), blurRadius = 10.px)
                }
            }

            rule(".skill-subtitle") {
                color = Color("var(--text-secondary)")
                fontSize = 0.9.rem
                marginBottom = 15.px
            }

            rule(".skill-details") {
                fontSize = 0.9.rem
                color = Color("var(--text-secondary)")
                lineHeight = LineHeight("1.6")
            }

            // Glowing corner accents
            rule(".skill-card-front::before, .skill-card-front::after, .skill-card-back::before, .skill-card-back::after") {
                content = QuotedString("")
                position = Position.absolute
                width = 40.px
                height = 40.px
                borderColor = Color("var(--primary)")
                opacity = 0.6
                put("transition", "all 0.3s ease")
            }

            rule(".skill-card-front::before") {
                top = 0.px
                left = 0.px
                borderTop = Border(2.px, BorderStyle.solid)
                borderLeft = Border(2.px, BorderStyle.solid)
                borderTopLeftRadius = LinearDimension("var(--border-radius-md)")
            }

            rule(".skill-card-front::after") {
                top = 0.px
                right = 0.px
                borderTop = Border(2.px, BorderStyle.solid)
                borderRight = Border(2.px, BorderStyle.solid)
                borderTopRightRadius = LinearDimension("var(--border-radius-md)")
            }

            rule(".skill-card-back::before") {
                bottom = 0.px
                left = 0.px
                borderBottom = Border(2.px, BorderStyle.solid)
                borderLeft = Border(2.px, BorderStyle.solid)
                borderBottomLeftRadius = LinearDimension("var(--border-radius-md)")
            }

            rule(".skill-card-back::after") {
                bottom = 0.px
                right = 0.px
                borderBottom = Border(2.px, BorderStyle.solid)
                borderRight = Border(2.px, BorderStyle.solid)
                borderBottomRightRadius = LinearDimension("var(--border-radius-md)")
            }

            rule(".skill-card:hover .skill-card-front::before, .skill-card:hover .skill-card-front::after, .skill-card:hover .skill-card-back::before, .skill-card:hover .skill-card-back::after") {
                width = 60.px
                height = 60.px
                opacity = 1
            }

            // Background animations
            rule(".skill-card-bg") {
                position = Position.absolute
                top = 0.px
                left = 0.px
                width = 100.pct
                height = 100.pct
                zIndex = (-1)
                overflow = Overflow.hidden
                borderRadius = LinearDimension("var(--border-radius-lg)")
            }

            rule(".skill-card-bg-circle") {
                position = Position.absolute
                borderRadius = 50.pct
                background =
                    "radial-gradient(circle, rgba(var(--primary-rgb), 0.3) 0%, rgba(var(--primary-rgb), 0) 70%)"
                put("animation", "moveAround 20s infinite linear")
            }

            rule(".skill-card-bg-circle:nth-child(1)") {
                width = 200.px
                height = 200.px
                top = (-100).px
                left = (-100).px
                put("animation-duration", "30s")
            }

            rule(".skill-card-bg-circle:nth-child(2)") {
                width = 150.px
                height = 150.px
                bottom = (-50).px
                right = (-50).px
                put("animation-duration", "20s")
                put("animation-direction", "reverse")
            }

            // Detail list on back
            rule(".skill-detail-list") {
                textAlign = TextAlign.left
                listStyleType = ListStyleType.none
                padding = Padding(0.px)
                margin = Margin(0.px)
                width = 100.pct
            }

            rule(".skill-detail-item") {
                marginBottom = 8.px
                display = Display.flex
                alignItems = Align.center
            }

            rule(".skill-detail-item::before") {
                content = QuotedString("")
                display = Display.inlineBlock
                width = 6.px
                height = 6.px
                borderRadius = 50.pct
                backgroundColor = Color("var(--primary)")
                marginRight = 10.px
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(var(--primary-rgb), 0.8)"), blurRadius = 5.px)
                }
            }

            // Media queries
            media("(max-width: 768px)") {
                rule(".skill-cards-container") {
                    gridTemplateColumns = GridTemplateColumns("repeat(auto-fit, minmax(200px, 1fr))")
                    gap = 20.px
                }

                rule(".skill-card") {
                    height = 240.px
                }

                rule(".skill-logo") {
                    width = 80.px
                    height = 80.px
                }

                rule(".skill-title") {
                    fontSize = 1.3.rem
                }
            }
        }
    }
}