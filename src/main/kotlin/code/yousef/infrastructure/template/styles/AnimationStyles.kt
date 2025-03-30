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
                animation = Animations().apply {
                    this += Animation(
                        duration = 0.6.s,
                        fillMode = FillMode.forwards,
                        timing = Timing.ease,
                        name = "fadeIn",
                        delay = 0.s,
                        iterationCount = IterationCount("1"),
                        direction = AnimationDirection.normal,
                        playState = PlayState.running
                    )
                }
            }

            keyframes("fadeIn") {
                to {
                    opacity = 1
                    transform { translateY(0.px) }
                }
            }

            // Pulse animation
            rule(".pulse") {
                animation = Animations().apply {
                    this += Animation(
                        duration = 2.s,
                        timing = Timing.easeInOut,
                        iterationCount = IterationCount.infinite,
                        name = "pulse",
                        delay = 0.s,
                        direction = AnimationDirection.alternate,
                        fillMode = FillMode.forwards,
                        playState = PlayState.running
                    )
                }
            }

            keyframes("pulse") {
                from {
                    opacity = 1.0
                }
                to {
                    opacity = 0.7
                }
            }

            // Floating animation
            rule(".floating") {
                animation = Animations().apply {
                    this += Animation(
                        duration = 3.s,
                        timing = Timing.easeInOut,
                        iterationCount = IterationCount.infinite,
                        name = "floating",
                        delay = 0.s,
                        direction = AnimationDirection.alternate,
                        fillMode = FillMode.forwards,
                        playState = PlayState.running
                    )
                }
            }
            
            keyframes("floating") {
                from {
                    transform { translateY(0.px) }
                }
                to {
                    transform { translateY((-10).px) }
                }
                from {
                    transform { translateY(0.px) }
                }
            }

            // Circuit animations
            keyframes("pulseLine") {
                from { opacity = 0.2 }
                to { opacity = 0.6 }
            }

            keyframes("nodePulse") {
                from {
                    transform { scale(0.8) }
                    boxShadow = BoxShadows().apply {
                        this += BoxShadow(color = Color("rgba(var(--primary-rgb), 0.5)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)
                    }
                }
                to {
                    transform { scale(1.2) }
                    boxShadow = BoxShadows().apply {
                        this += BoxShadow(color = Color("rgba(var(--primary-rgb), 0.8)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 20.px)
                    }
                }
            }
            
            // Move Around animation for skill card backgrounds
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
            
            // Glow pulse for skill icons
            keyframes("glowPulse") {
                from {
                    filter = "drop-shadow(0 0 5px rgba(var(--primary-rgb), 0.5))"
                }
                to {
                    filter = "drop-shadow(0 0 20px rgba(var(--primary-rgb), 0.8))"
                }
                from {
                    filter = "drop-shadow(0 0 5px rgba(var(--primary-rgb), 0.5))"
                }
            }
        }
    }
}