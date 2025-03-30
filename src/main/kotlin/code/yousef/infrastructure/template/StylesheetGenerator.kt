package code.yousef.infrastructure.template

import jakarta.enterprise.context.ApplicationScoped
import kotlinx.css.*
import kotlinx.css.properties.*

@ApplicationScoped
class StylesheetGenerator {

    fun generateStyles(): String {
        return CssBuilder().apply {
            rule(":root") {
                put("--theme-transition", "all 0.3s ease")

                // Typography system
                put("--font-primary", "'Space Grotesk', sans-serif")
                put("--font-secondary", "'Inter', sans-serif")
                put("--line-height-body", "1.6")
                put("--line-height-heading", "1.2")
                put("--letter-spacing", "0.01em")

                // Spacing system
                put("--space-xs", "4px")
                put("--space-sm", "8px")
                put("--space-md", "16px")
                put("--space-lg", "32px")
                put("--space-xl", "64px")

                // Dark theme (default) - more refined palette
                put("--base", "#0a0a14")              // Deep space blue
                put("--cyber-cyan", "#00c9db")
                put("--neon-pink", "#d63384")         // Muted professional pink
                put("--neon-purple", "#7952b3")       // Muted purple
                put("--neon-green", "#00b894")        // Muted professional green
                put("--neon-orange", "#e17055")       // Muted orange
                put("--neon-blue", "#0984e3")         // Professional blue
                put("--neon-yellow", "#fdcb6e")       // Muted yellow
                put("--gradient-start", "#0a0a14")    // Deep space blue start
                put("--gradient-end", "#1a1a2e")       // Slightly lighter purple end
                put("--text", "#e2e8f0")              // Off-white text for better readability
                put("--surface", "rgba(10, 10, 20, 0.7)")// Dark professional surface
                put("--glass-border", "rgba(0, 201, 219, 0.3)") // Subtle border
                put("--glow-intensity", "8px")       // More subtle glow
            }

            // Light theme
            rule("[data-theme='light']") {
                put("--base", "#f8fafc")              // Off-white base
                put("--cyber-cyan", "#0891b2")        // Professional teal for light mode
                put("--neon-pink", "#be185d")         // Professional pink for light mode
                put("--neon-purple", "#6d28d9")       // Professional purple for light mode
                put("--neon-green", "#059669")        // Professional green for light mode
                put("--neon-orange", "#ea580c")       // Professional orange for light mode
                put("--neon-blue", "#2563eb")         // Professional blue for light mode
                put("--neon-yellow", "#d97706")       // Professional amber for light mode
                put("--gradient-start", "#f8fafc")    // Off-white start
                put("--gradient-end", "#e0e7ff")      // Very light indigo end
                put("--text", "#0f172a")              // Dark slate text for contrast
                put("--surface", "rgba(248, 250, 252, 0.85)") // Light professional surface
                put("--glass-border", "rgba(8, 145, 178, 0.3)") // Subtle professional border
                put("--glow-intensity", "5px")        // Even subtler glow
            }

            // Global styles with refined typography
            body {
                margin = Margin(0.px)
                padding = Padding(0.px)
                fontFamily = "var(--font-secondary)"
                background = "radial-gradient(circle at 50% 50%, var(--gradient-start), var(--gradient-end))"
                color = Color("var(--text)")
                position = Position.relative
                overflow = Overflow.auto
                height = LinearDimension.auto
                minHeight = 100.vh
                lineHeight = LineHeight("var(--line-height-body)")
                letterSpacing = LinearDimension("var(--letter-spacing)")
                fontSize = 16.px
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            h1; h2; h3; h4; h5; h6 {
            fontFamily = "var(--font-primary)"
            lineHeight = LineHeight("var(--line-height-heading)")
            marginTop = 0.px
            marginBottom = 16.px
            fontWeight = FontWeight.w500
        }

            // Theme toggle styling
            rule(".theme-toggle") {
                position = Position.fixed
                bottom = 20.px
                right = 20.px
                zIndex = 1000
                width = 44.px
                height = 44.px
                borderRadius = 50.pct
                backgroundColor = Color("var(--surface)")
                border = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                cursor = Cursor.pointer
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.2)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 8.px)
                }
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            // Performance toggle styling - position to the LEFT of theme toggle
            rule(".performance-toggle") {
                position = Position.fixed
                bottom = 20.px  // Same bottom as theme toggle
                right = 74.px  // Position it to the left of theme toggle
                zIndex = 1000
                height = 44.px  // Same height as theme toggle
                display = Display.flex
                alignItems = Align.center
                padding = Padding(8.px, 12.px)
                borderRadius = 22.px
                backgroundColor = Color("var(--surface)")
                border = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                cursor = Cursor.pointer
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.2)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 8.px)
                }
                color = Color("var(--text)")
                fontSize = 13.px
                fontWeight = FontWeight.normal
            }

            // Make sure both SVGs are visible but with proper coloring
            rule(".sun") {
                put("fill", "#F9D71C")  // Yellowish color for sun
                put("stroke", "#F9D71C")
            }

            rule(".moon") {
                put("fill", "#CCCCFF")  // Light purple/blue for moon
                put("stroke", "#CCCCFF")
            }

            rule(".theme-toggle:hover") {
                transform {
                    translateY((-2).px)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(214, 51, 132, 0.2)"), offsetX = 0.px, offsetY = 4.px, blurRadius = 12.px)
                }
            }

            rule(".theme-toggle svg") {
                width = 20.px
                height = 20.px
                transition = Transitions().apply {
                    this += Transition("transform", 0.5.s, Timing.easeInOut)
                }
            }

            rule("[data-theme='light'] .moon") {
                display = Display.none
            }
            rule("[data-theme='light'] .sun") {
                display = Display.block
            }

            rule(":not([data-theme='light']) .sun") {
                display = Display.none
            }

            rule(":not([data-theme='light']) .moon") {
                display = Display.block
            }

            rule("[data-theme='light'] .theme-toggle") {
                borderColor = Color("rgba(8, 145, 178, 0.3)")
                backgroundColor = Color("rgba(248, 250, 252, 0.9)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(8, 145, 178, 0.2)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 8.px)
                }
            }

            // Canvas background
            rule("#bg-canvas") {
                position = Position.fixed
                top = 0.px
                left = 0.px
                width = 100.pct
                height = 100.pct
                zIndex = -1
                pointerEvents = PointerEvents.none
                opacity = 0.85 // Slightly more subtle background
            }

            // Header with refined glass effect
            header {
                padding = Padding(24.px, 32.px)
                position = Position.sticky
                top = 0.px
                zIndex = 100
                backdropFilter = "blur(10px)"
                backgroundColor = Color("var(--surface)")
                borderBottom = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.15)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 10.px)
                }
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
                marginBottom = 40.px
            }

            // Refined glowing text
            rule(".glowing-text") {
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = LinearDimension("var(--glow-intensity)"))
                }
                transition = Transitions().apply {
                    this += Transition("text-shadow", 0.3.s, Timing.ease)
                }
                fontWeight = FontWeight.w600
                letterSpacing = 0.5.px
            }

            // Professional navigation
            nav {
                ul {
                    display = Display.flex
                    listStyleType = ListStyleType.none
                    padding = Padding(0.px)
                    margin = Margin(0.px)
                    gap = 24.px
                }

                li {
                    position = Position.relative
                }

                a {
                    color = Color("var(--text)")
                    textDecoration = TextDecoration.none
                    fontSize = 1.rem
                    fontWeight = FontWeight.w400
                    padding = Padding(8.px, 12.px)
                    transition = Transitions().apply {
                        this += Transition("all", 0.3.s, Timing.ease)
                    }
                    position = Position.relative
                    letterSpacing = 0.3.px

                    before {
                        content = QuotedString("")
                        position = Position.absolute
                        bottom = 0.px
                        left = 0.px
                        width = 0.pct
                        height = 1.px
                        backgroundColor = Color("var(--cyber-cyan)")
                        transition = Transitions().apply {
                            this += Transition("width", 0.3.s, Timing.ease)
                        }
                    }
                }

                rule("nav a:hover") {
                    color = Color("var(--cyber-cyan)")
                    textShadow = TextShadows().apply {
                        this += TextShadow(color = Color("rgba(0, 201, 219, 0.3)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 4.px)
                    }
                }

                rule("nav a:hover::before") {
                    width = 100.pct
                }
            }

            // Refined Professional Glass Container
            rule(".glass-container") {
                backdropFilter = "blur(10px)"
                backgroundColor = Color("var(--surface)")
                borderRadius = 8.px
                padding = Padding(48.px, 40.px)
                margin = Margin(0.px, LinearDimension.auto)
                marginBottom = 120.px
                border = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.15)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 15.px)
                }
                maxWidth = 1400.px
                width = 90.pct
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            // Refined Glass Morphic elements
            rule(".glass-morphic") {
                backdropFilter = "blur(10px)"
                backgroundColor = Color("rgba(10, 10, 20, 0.65)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.1)"), offsetX = 0.px, offsetY = 5.px, blurRadius = 15.px, spreadRadius = (-8).px)
                }
                borderRadius = 8.px
                border = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease) // Exponential ease-out for refinement
                }
            }

            rule(".glass-morphic:hover") {
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(214, 51, 132, 0.15)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 12.px)
                }
                borderColor = Color("var(--neon-pink)")
                transform {
                    translateY((-2).px)
                }
            }

            // Projects container - more professional layout
            rule(".projects-container") {
                width = 100.pct
                maxWidth = 1200.px
                margin = Margin(0.px, LinearDimension.auto)
                padding = Padding(0.px, 16.px)
            }

            // Projects grid layout - more professional spacing
            rule(".projects-grid") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(2, 1fr)")
                gap = 40.px
                padding = Padding(20.px, 0.px)
            }

            // Refined Project Cards with subtle 3D effect
            rule(".project-card") {
                position = Position.relative
                height = 350.px
                transform { perspective(1000.px) }
                margin = Margin(0.px, 0.px, 40.px, 0.px)

                hover {
                    "& .card-inner" {
                        transform { rotateY(175.deg) } // Less extreme rotation for subtlety
                    }
                }
            }
            rule(".project-image") {
                width = 100.pct
                height = 160.px
                overflow = Overflow.hidden
                marginBottom = 16.px
                borderRadius = 6.px
            }

            rule(".project-image img") {
                width = 100.pct
                height = 100.pct
                objectFit = ObjectFit.cover
                transition = Transitions().apply {
                    this += Transition("transform", 0.5.s, Timing.ease)
                }
            }

            rule(".project-card:hover .project-image img") {
                transform { scale(1.05) }
            }

            rule(".card-inner") {
                width = 100.pct
                height = 100.pct
                position = Position.relative
                transition = Transitions().apply {
                    this += Transition("transform", 0.6.s, Timing.ease) // Refined animation curve
                }
                put("transform-style", "preserve-3d")
            }

            rule(".card-front, .card-back") {
                position = Position.absolute
                width = 100.pct
                height = 100.pct
                put("backface-visibility", "hidden")
                padding = Padding(32.px)
                borderRadius = 8.px
                backdropFilter = "blur(10px)"
                backgroundColor = Color("var(--surface)")
                border = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxSizing = BoxSizing.borderBox
                overflow = Overflow.hidden
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.1)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 8.px)
                }
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            rule(".card-front h3") {
                color = Color("var(--cyber-cyan)")
                fontSize = 1.6.rem
                marginTop = 0.px
                marginBottom = 16.px
                fontWeight = FontWeight.w500
            }

            rule(".card-front p") {
                fontSize = 1.rem
                lineHeight = LineHeight("var(--line-height-body)")
                marginBottom = 24.px
                color = Color("var(--text)")
                maxHeight = 120.px
                overflow = Overflow.hidden
            }

            rule(".card-back") {
                transform { rotateY(180.deg) }
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.center
                alignItems = Align.center
                backgroundColor = Color("var(--surface)")
            }

            // 3D model container - refined styling
            rule(".model-container") {
                width = 100.pct
                height = 180.px
                marginBottom = 24.px
                backgroundColor = Color("var(--base)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.3)"))
                borderRadius = 6.px
                overflow = Overflow.hidden
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.15)"), offsetX = 0.px, offsetY = 4.px, blurRadius = 16.px, spreadRadius = (-8).px)
                }
            }

            // Tech stack styling - more professional pills
            rule(".tech-stack") {
                display = Display.flex
                flexWrap = FlexWrap.wrap
                gap = 8.px
                marginTop = 16.px
                marginBottom = 16.px
            }

            rule(".tech-pill") {
                display = Display.inlineBlock
                padding = Padding(4.px, 10.px)
                background = "rgba(0, 201, 219, 0.1)"
                borderRadius = 4.px
                fontSize = 0.85.rem
                fontWeight = FontWeight.w400
                color = Color("var(--cyber-cyan)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.2)"))
                transition = Transitions().apply {
                    this += Transition("all", 0.2.s, Timing.ease)
                }
            }

            rule(".tech-pill:hover") {
                background = "rgba(214, 51, 132, 0.1)"
                color = Color("var(--neon-pink)")
                borderColor = Color("rgba(214, 51, 132, 0.2)")
                transform {
                    translateY((-1).px)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(214, 51, 132, 0.1)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 4.px)
                }
            }

            // Project links - more professional buttons
            rule(".project-links") {
                display = Display.flex
                gap = 16.px
                marginTop = 24.px
                width = 100.pct
                justifyContent = JustifyContent.center
            }

            rule(".btn") {
                display = Display.inlineBlock
                padding = Padding(8.px, 16.px)
                fontSize = 0.95.rem
                fontWeight = FontWeight.w500
                letterSpacing = 0.5.px
                textAlign = TextAlign.center
                borderRadius = 6.px
                textDecoration = TextDecoration.none
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            rule(".neon-btn") {
                color = Color("var(--cyber-cyan)")
                backgroundColor = Color("rgba(10, 10, 20, 0.7)")
                border = Border(1.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.1)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 8.px)
                }
                position = Position.relative
                overflow = Overflow.hidden
                zIndex = 1
            }

            rule(".neon-btn::before") {
                content = QuotedString("")
                position = Position.absolute
                top = 0.px
                left = (-100).pct
                width = 100.pct
                height = 100.pct
                background = "linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.1), transparent)"
                zIndex = -1
                transition = Transitions().apply {
                    this += Transition("left", 0.5.s, Timing.ease)
                }
            }

            rule(".neon-btn:hover") {
                color = Color("var(--neon-pink)")
                backgroundColor = Color("rgba(10, 10, 20, 0.8)")
                borderColor = Color("var(--neon-pink)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(214, 51, 132, 0.15)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 10.px)
                }
                transform {
                    translateY((-2).px)
                }
            }

            rule(".neon-btn:hover::before") {
                left = 100.pct
            }

            // Refined footer
            footer {
                padding = Padding(24.px)
                textAlign = TextAlign.center
                backdropFilter = "blur(10px)"
                backgroundColor = Color("var(--surface)")
                borderTop = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.1)"), offsetX = 0.px, offsetY = (-2).px, blurRadius = 10.px)
                }
                position = Position.relative
                zIndex = 10
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
                fontSize = 0.9.rem
                color = Color("rgba(226, 232, 240, 0.8)")
            }

            // Refined terminal container
            rule(".terminal-container") {
                position = Position.relative
                width = 100.pct
                maxWidth = 900.px
                margin = Margin(2.rem, LinearDimension.auto, 3.rem, LinearDimension.auto)
                backdropFilter = "blur(10px)"
                backgroundColor = Color("var(--surface)")
                border = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                borderRadius = 8.px
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.15)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 15.px)
                }
                overflow = Overflow.hidden
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
                display = Display.block
            }

            // Section styling
            rule("section") {
                marginBottom = 80.px
            }

            rule("section h2") {
                fontSize = 2.rem
                marginBottom = 24.px
                position = Position.relative
                display = Display.inlineBlock
                fontWeight = FontWeight.w500
                color = Color("var(--cyber-cyan)")
            }

            rule("section h2::after") {
                content = QuotedString("")
                position = Position.absolute
                bottom = (-8).px
                left = 0.px
                width = 100.pct
                height = 1.px
                background = "linear-gradient(90deg, var(--cyber-cyan), transparent)"
                borderRadius = 1.px
            }

            // Content divider
            rule(".content-divider") {
                width = 100.pct
                height = 1.px
                margin = Margin(48.px, 0.px)
                position = Position.relative
                background = "linear-gradient(90deg, transparent, rgba(0, 201, 219, 0.3), transparent)"
            }

            // Refined holographic Menu
            rule(".side-navigation") {
                position = Position.fixed
                top = 50.pct
                right = 20.px
                transform {
                    translateY((-50).pct)
                }
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = 16.px
                zIndex = 1000
                backdropFilter = "blur(10px)"
                backgroundColor = Color("rgba(10, 10, 20, 0.5)")
                padding = Padding(12.px)
                borderRadius = 8.px
                border = Border(1.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.15)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 15.px)
                }
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            rule(".nav-icon") {
                width = 40.px
                height = 40.px
                borderRadius = 6.px
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                backgroundColor = Color("rgba(10, 10, 20, 0.6)")
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.2)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.1)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 6.px)
                }
                cursor = Cursor.pointer
                transition = Transitions().apply {
                    this += Transition("all", 0.2.s, Timing.ease)
                }
            }

            rule(".nav-icon svg") {
                width = 20.px
                height = 20.px
                put("stroke", "var(--cyber-cyan)")
                transition = Transitions().apply {
                    this += Transition("all", 0.2.s, Timing.ease)
                }
            }

            rule(".nav-icon:hover") {
                backgroundColor = Color("rgba(10, 10, 20, 0.8)")
                borderColor = Color("rgba(214, 51, 132, 0.3)")
                transform {
                    translateX((-4).px)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(214, 51, 132, 0.15)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 8.px)
                }
            }

            rule(".nav-icon:hover svg") {
                put("stroke", "var(--neon-pink)")
            }

            // Contact section with refined styling
            rule(".contact-container") {
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = 24.px
                maxWidth = 700.px
                margin = Margin(40.px, LinearDimension.auto)
                padding = Padding(32.px)
            }

            rule(".contact-item") {
                display = Display.flex
                alignItems = Align.center
                padding = Padding(16.px, 20.px)
                backgroundColor = Color("rgba(10, 10, 20, 0.5)")
                borderRadius = 6.px
                border = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.2)"))
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.05)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 6.px)
                }
            }

            rule(".contact-item:hover") {
                transform {
                    translateX(4.px)
                }
                borderColor = Color("rgba(214, 51, 132, 0.3)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(214, 51, 132, 0.1)"), offsetX = 0.px, offsetY = 2.px, blurRadius = 8.px)
                }
                backgroundColor = Color("rgba(10, 10, 20, 0.6)")
            }

            rule(".contact-icon") {
                fontSize = 1.5.rem
                marginRight = 16.px
                color = Color("var(--cyber-cyan)")
                transition = Transitions().apply {
                    this += Transition("color", 0.3.s, Timing.ease)
                }
            }

            rule(".contact-item:hover .contact-icon") {
                color = Color("var(--neon-pink)")
            }

            rule(".contact-item a") {
                color = Color("var(--text)")
                textDecoration = TextDecoration.none
                fontSize = 1.rem
                transition = Transitions().apply {
                    this += Transition("color", 0.3.s, Timing.ease)
                }
            }

            rule(".contact-item:hover a") {
                color = Color("var(--neon-pink)")
            }

            // Terminal styles with more professional look
            rule(".terminal") {
                fontFamily = "'Space Mono', monospace"
                color = Color("var(--cyber-cyan)")
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("rgba(0, 201, 219, 0.3)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 3.px)
                }
                height = 100.pct
                display = Display.flex
                flexDirection = FlexDirection.column
            }

            rule(".terminal-header") {
                backgroundColor = Color("rgba(10, 10, 20, 0.7)")
                padding = Padding(0.6.rem, 1.rem)
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                borderBottom = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.2)"))
            }

            rule(".terminal-title") {
                fontSize = 0.85.rem
                color = Color("var(--cyber-cyan)")
                fontWeight = FontWeight.w500
            }

            rule(".terminal-controls") {
                display = Display.flex
                gap = 0.5.rem
            }

            rule(".terminal-control") {
                width = 10.px
                height = 10.px
                borderRadius = 50.pct
                cursor = Cursor.pointer
            }

            rule(".terminal-control.close") {
                backgroundColor = Color("#ff5f56")
            }

            rule(".terminal-control.minimize") {
                backgroundColor = Color("#ffbd2e")
            }

            rule(".terminal-control.maximize") {
                backgroundColor = Color("#27c93f")
            }

            rule(".terminal-content") {
                padding = Padding(1.rem)
                height = 300.px
                overflowY = Overflow.auto
            }

            rule(".terminal-line") {
                marginBottom = 0.6.rem
                display = Display.flex
                alignItems = Align.flexStart
                gap = 0.6.rem
            }

            rule(".prompt") {
                color = Color("var(--cyber-cyan)")
                fontWeight = FontWeight.bold
            }

            rule(".command") {
                color = Color("rgba(226, 232, 240, 0.9)")
            }

            rule(".output") {
                marginTop = 0.5.rem
                color = Color("rgba(226, 232, 240, 0.7)")
                lineHeight = LineHeight("1.5")
                fontSize = 0.9.rem
                paddingLeft = 16.px
            }

            rule(".terminal-input-line") {
                display = Display.flex
                alignItems = Align.center
                gap = 0.5.rem
                padding = Padding(0.5.rem)
                backgroundColor = Color("rgba(10, 10, 20, 0.8)")
                borderTop = Border(1.px, BorderStyle.solid, Color("rgba(0, 201, 219, 0.1)"))
            }

            rule(".terminal-input") {
                background = "transparent"
                border = Border.none
                color = Color("var(--cyber-cyan)")
                fontFamily = "'Space Mono', monospace"
                fontSize = 0.95.rem
                flex = Flex(1)
                outline = Outline.none
            }

            rule(".terminal-input::placeholder") {
                color = Color("rgba(0, 201, 219, 0.4)")
            }

            // Terminal scrollbar styling
            rule(".terminal-content::-webkit-scrollbar") {
                width = 6.px
            }

            rule(".terminal-content::-webkit-scrollbar-track") {
                backgroundColor = Color("rgba(10, 10, 20, 0.3)")
            }

            rule(".terminal-content::-webkit-scrollbar-thumb") {
                backgroundColor = Color("rgba(0, 201, 219, 0.2)")
                borderRadius = 3.px
            }

            rule(".terminal-content::-webkit-scrollbar-thumb:hover") {
                backgroundColor = Color("rgba(0, 201, 219, 0.3)")
            }

            // Media queries for responsive design
            media("(max-width: 768px)") {
                rule(".projects-grid") {
                    gridTemplateColumns = GridTemplateColumns("1fr")
                }

                rule(".side-navigation") {
                    right = 10.px
                }

                rule("header") {
                    flexDirection = FlexDirection.column
                    alignItems = Align.center
                    padding = Padding(16.px, 12.px)
                    gap = 16.px
                }

                rule(".glass-container") {
                    padding = Padding(24.px, 16.px)
                    width = 95.pct
                }
            }

            // Low performance device optimizations
            rule(".low-performance .glass-morphic") {
                backdropFilter = "blur(5px)"  // Less intense blur
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 201, 219, 0.1)"), offsetX = 0.px, offsetY = 1.px, blurRadius = 4.px)  // Smaller shadow
                }
            }

            rule(".low-performance #bg-canvas") {
                opacity = 0.6  // Less intense background
            }

            rule(".low-performance .glowing-text") {
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("rgba(0, 201, 219, 0.4)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 4.px)  // Less intense glow
                }
            }

            // Option to disable 3D effects entirely
            rule(".no-3d #bg-canvas, .no-3d .terminal-container, .no-3d .model-container") {
                display = Display.none
            }

            rule(".no-3d .content-container") {
                background = "radial-gradient(circle at 50% 0%, var(--gradient-start), var(--gradient-end))"
            }
        }.toString()
    }
}