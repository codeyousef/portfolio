package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import kotlinx.css.properties.*

class AnimationStyles : StyleGenerator {
    override fun generateStyles(cssBuilder: CssBuilder) {
        with(cssBuilder) {
            // Fade in animation
            rule(".fade-in") {
                opacity = 0
                transform { translateY(20.px) }
                put("animation", "fadeIn 0.6s ease forwards")
            }

            keyframes("fadeIn") {
                to {
                    opacity = 1
                    transform { translateY(0.px) }
                }
            }

            // Floating animation
            rule(".floating") {
                put("animation", "floating 3s ease-in-out infinite")
            }

            keyframes("floating") {
                from {
                    transform { translateY(0.px) }
                }
                to {
                    transform { translateY((-10).px) }
                }
            }

            // Pulse animation
            rule(".pulse") {
                put("animation", "pulse 2s ease-in-out infinite")
            }

            keyframes("pulse") {
                from {
                    opacity = 1
                }
                to {
                    opacity = 0.7
                }
            }

            // Circuit animations
            keyframes("pulseLine") {
                from {
                    opacity = 0.2
                }
                to {
                    opacity = 0.6
                }
            }

            keyframes("nodePulse") {
                from {
                    transform { scale(0.8) }
                    boxShadow = BoxShadows().apply {
                        this += BoxShadow(color = Color("rgba(var(--primary-rgb), 0.5)"), blurRadius = 5.px)
                    }
                }
                to {
                    transform { scale(1.2) }
                    boxShadow = BoxShadows().apply {
                        this += BoxShadow(color = Color("rgba(var(--primary-rgb), 0.8)"), blurRadius = 20.px)
                    }
                }
            }

            // Orbit animation for skill card backgrounds
            keyframes("moveAround") {
                from {
                    transform {
                        rotate(0.deg)
                        translateX(30.px)
                        rotate(0.deg)
                    }
                }
                to {
                    transform {
                        rotate(360.deg)
                        translateX(30.px)
                        rotate((-360).deg)
                    }
                }
            }

            // Type animation for terminal
            keyframes("blink") {
                from {
                    opacity = 1
                }
                to {
                    opacity = 0
                }
            }

            // Glow pulse
            keyframes("glowPulse") {
                from {
                    boxShadow = BoxShadows().apply {
                        this += BoxShadow(color = Color("rgba(var(--primary-rgb), 0.2)"), blurRadius = 5.px)
                    }
                }
                to {
                    boxShadow = BoxShadows().apply {
                        this += BoxShadow(color = Color("rgba(var(--primary-rgb), 0.8)"), blurRadius = 20.px)
                    }
                }
            }
        }
    }
}