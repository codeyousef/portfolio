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
                from {
                    opacity = 0
                    transform { translateY(20.px) }
                }
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
                0 {
                    transform { translateY(0.px) }
                }
                50 {
                    transform { translateY((-10).px) }
                }
                100 {
                    transform { translateY(0.px) }
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
                50 {
                    opacity = 0.7
                }
                to {
                    opacity = 1
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
                    put("box-shadow", "0 0 5px rgba(var(--primary-rgb), 0.5)")
                }
                to {
                    transform { scale(1.2) }
                    put("box-shadow", "0 0 20px rgba(var(--primary-rgb), 0.8)")
                }
            }

            // Orbit animation for skill card backgrounds
            keyframes("moveAround") {
                from {
                    put("transform", "rotate(0deg) translateX(30px) rotate(0deg)")
                }
                to {
                    put("transform", "rotate(360deg) translateX(30px) rotate(-360deg)")
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
                    put("box-shadow", "0 0 5px rgba(var(--primary-rgb), 0.2)")
                }
                to {
                    put("box-shadow", "0 0 20px rgba(var(--primary-rgb), 0.8)")
                }
            }
        }
    }
}