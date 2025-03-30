package code.yousef.infrastructure.template

import jakarta.enterprise.context.ApplicationScoped
import kotlinx.css.*
import kotlinx.css.properties.*

@ApplicationScoped
class StylesheetGenerator {

    fun generateStyles(): String {
        return CssBuilder().apply {
            // ULTRA VIBRANT cyberpunk color palette
            rule(":root") {
                put("--theme-transition", "all 0.3s ease")
                
                // Dark theme (default)
                put("--base", "#000000")              // Black base
                put("--cyber-cyan", "#00ffff")        // Electric cyan
                put("--neon-pink", "#ff00ff")         // Hot pink
                put("--neon-purple", "#9900ff")       // Bright purple
                put("--neon-green", "#00ff00")        // Electric green
                put("--neon-orange", "#ff6600")       // Bright orange
                put("--neon-blue", "#0066ff")         // Electric blue
                put("--neon-yellow", "#ffff00")       // Electric yellow
                put("--gradient-start", "#000000")    // Dark start
                put("--gradient-end", "#220044")      // Deep purple end
                put("--text", "#ffffff")              // White text
                put("--surface", "rgba(0, 0, 0, 0.7)")// Dark surface
                put("--glass-border", "rgba(0, 255, 255, 0.5)") // Bright border
                put("--glow-intensity", "15px")       // Strong glow
            }
            
            // Light theme
            rule("[data-theme='light']") {
                put("--base", "#ffffff")              // White base
                put("--cyber-cyan", "#00bbff")        // Blue for light mode
                put("--neon-pink", "#ff0066")         // Pink for light mode
                put("--neon-purple", "#8800cc")       // Purple for light mode
                put("--neon-green", "#00cc00")        // Green for light mode
                put("--neon-orange", "#ff5500")       // Orange for light mode
                put("--neon-blue", "#0055ff")         // Blue for light mode
                put("--neon-yellow", "#ffcc00")       // Yellow for light mode
                put("--gradient-start", "#ffffff")    // White start
                put("--gradient-end", "#e6e6ff")      // Light purple end
                put("--text", "#000000")              // Black text
                put("--surface", "rgba(255, 255, 255, 0.8)") // Light surface
                put("--glass-border", "rgba(0, 170, 255, 0.5)") // Blue border
                put("--glow-intensity", "10px")       // Subtler glow
            }

            // Global styles with fixed scrolling
            body {
                margin = Margin(0.px)
                padding = Padding(0.px)
                fontFamily = "'Space Grotesk', sans-serif"
                background = "radial-gradient(circle, var(--gradient-start), var(--gradient-end))"
                color = Color("var(--text)")
                position = Position.relative
                overflow = Overflow.auto
                height = LinearDimension.auto
                minHeight = 100.vh
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            // Theme toggle styling
            rule(".theme-toggle") {
                position = Position.fixed
                bottom = 20.px
                right = 20.px
                zIndex = 1000
                width = 50.px
                height = 50.px
                borderRadius = 50.pct
                backgroundColor = Color("var(--surface)")
                border = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                cursor = Cursor.pointer
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            // Performance toggle styling - position to the LEFT of theme toggle
            rule(".performance-toggle") {
                position = Position.fixed
                bottom = 20.px  // Same bottom as theme toggle
                right = 90.px  // Position it to the left of theme toggle
                zIndex = 1000
                height = 50.px  // Same height as theme toggle
                display = Display.flex
                alignItems = Align.center
                padding = Padding(10.px, 15.px)
                borderRadius = 25.px
                backgroundColor = Color("var(--surface)")
                border = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                cursor = Cursor.pointer
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
                color = Color("var(--text)")
                fontSize = 14.px
                fontWeight = FontWeight.bold
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
                    scale(1.1)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--neon-pink)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
                }
            }
            
            rule(".theme-toggle svg") {
                width = 24.px
                height = 24.px
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
                borderColor = Color("rgba(0, 170, 255, 0.8)")
                backgroundColor = Color("rgba(255, 255, 255, 0.9)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(0, 170, 255, 0.8)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
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
            }

            // Header with glass effect
            header {
                padding = Padding(16.px)
                position = Position.sticky
                top = 0.px
                zIndex = 100
                backdropFilter = "blur(12px)"
                backgroundColor = Color("var(--surface)")
                borderBottom = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
                }
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            // Glowing Text - much brighter
            rule(".glowing-text") {
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = LinearDimension("var(--glow-intensity)"))
                }
                transition = Transitions().apply {
                    this += Transition("text-shadow", 0.3.s, Timing.ease)
                }
            }

            // Navigation
            nav {
                ul {
                    display = Display.flex
                    listStyleType = ListStyleType.none
                    padding = Padding(0.px)
                    margin = Margin(0.px)
                    gap = 20.px
                }
                
                li {
                    position = Position.relative
                }
                
                a {
                    color = Color("var(--text)")
                    textDecoration = TextDecoration.none
                    fontSize = 1.2.rem
                    fontWeight = FontWeight.w500
                    padding = Padding(8.px, 12.px)
                    transition = Transitions().apply {
                        this += Transition("all", 0.3.s, Timing.ease)
                    }
                    position = Position.relative
                    
                    before {
                        content = QuotedString("")
                        position = Position.absolute
                        bottom = 0.px
                        left = 0.px
                        width = 0.pct
                        height = 2.px
                        backgroundColor = Color("var(--neon-pink)")
                        transition = Transitions().apply {
                            this += Transition("width", 0.3.s, Timing.ease)
                        }
                    }
                }
                
                rule("nav a:hover") {
                    color = Color("var(--neon-pink)")
                    textShadow = TextShadows().apply {
                        this += TextShadow(color = Color("var(--neon-pink)"), offsetX = 0.px, offsetY = 0.px, blurRadius = LinearDimension("var(--glow-intensity)"))
                    }
                }
                
                rule("nav a:hover::before") {
                    width = 100.pct
                }
            }

            // Improved Glass Container
            rule(".glass-container") {
                backdropFilter = "blur(12px)"
                backgroundColor = Color("var(--surface)")
                borderRadius = 10.px
                padding = Padding(30.px)
                margin = Margin(20.px, LinearDimension.auto)
                marginBottom = 80.px
                border = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 20.px)
                }
                maxWidth = 1200.px
                width = 90.pct
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            // Glass Morphic elements
            rule(".glass-morphic") {
                backdropFilter = "blur(12px)"
                backgroundColor = Color("var(--surface)")
                borderRadius = 8.px
                border = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                transition = Transitions().apply {
                    this += Transition("all", 0.4.s, Timing.ease)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
            }
            
            rule(".glass-morphic:hover") {
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--neon-pink)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
                }
                borderColor = Color("var(--neon-pink)")
            }

            // Projects container - 2 columns, centered
            rule(".projects-container") {
                width = 100.pct
                maxWidth = 900.px
                margin = Margin(0.px, LinearDimension.auto)
            }
            
            // Projects grid layout
            rule(".projects-grid") {
                display = Display.grid
                gridTemplateColumns = GridTemplateColumns("repeat(2, 1fr)")
                gap = 30.px
                padding = Padding(20.px, 0.px)
            }

            // Project Cards with 3D flip effect
            rule(".project-card") {
                position = Position.relative
                height = 350.px
                transform {
                    perspective(1000.px)
                }
                
                hover {
                    "& .card-inner" {
                        transform { rotateY(180.deg) }
                    }
                }
            }

            rule(".card-inner") {
                width = 100.pct
                height = 100.pct
                position = Position.relative
                transition = Transitions().apply {
                    this += Transition("transform", 0.4.s, Timing.ease)
                }
                put("transform-style", "preserve-3d")
            }

            rule(".card-front, .card-back") {
                position = Position.absolute
                width = 100.pct
                height = 100.pct
                put("backface-visibility", "hidden")
                padding = Padding(20.px)
                borderRadius = 8.px
                backdropFilter = "blur(12px)"
                backgroundColor = Color("var(--surface)")
                border = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxSizing = BoxSizing.borderBox
                overflow = Overflow.hidden
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            rule(".card-front h3") {
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = LinearDimension("var(--glow-intensity)"))
                }
                fontSize = 1.8.rem
                marginTop = 0.px
                marginBottom = 15.px
            }
            
            rule(".card-front p") {
                fontSize = 1.rem
                lineHeight = LineHeight("1.6")
                marginBottom = 15.px
            }

            rule(".card-back") {
                transform { rotateY(180.deg) }
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.center
                alignItems = Align.center
                backgroundColor = Color("var(--surface)")
            }

            // 3D model container 
            rule(".model-container") {
                width = 100.pct
                height = 180.px
                marginBottom = 20.px
                backgroundColor = Color("var(--base)")
                border = Border(1.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
                borderRadius = 6.px
                overflow = Overflow.hidden
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
            }

            // Tech stack styling
            rule(".tech-stack") {
                display = Display.flex
                flexWrap = FlexWrap.wrap
                gap = 10.px
                marginTop = 15.px
                marginBottom = 15.px
            }

            rule(".tech-pill") {
                display = Display.inlineBlock
                padding = Padding(6.px, 12.px)
                background = "color-mix(in srgb, var(--cyber-cyan) 20%, transparent)"
                borderRadius = 20.px
                fontSize = 0.9.rem
                fontWeight = FontWeight.w500
                color = Color("var(--cyber-cyan)")
                border = Border(2.px, BorderStyle.solid, Color("color-mix(in srgb, var(--cyber-cyan) 50%, transparent)"))
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("color-mix(in srgb, var(--cyber-cyan) 30%, transparent)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)
                }
            }

            rule(".tech-pill:hover") {
                background = "color-mix(in srgb, var(--neon-pink) 20%, transparent)"
                color = Color("var(--neon-pink)")
                borderColor = Color("var(--neon-pink)")
                transform {  
                    translateY((-5).px)
                    scale(1.05)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--neon-pink)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
            }

            // Project links
            rule(".project-links") {
                display = Display.flex
                gap = 15.px
                marginTop = 20.px
                width = 100.pct
                justifyContent = JustifyContent.center
            }

            rule(".btn") {
                display = Display.inlineBlock
                padding = Padding(10.px, 20.px)
                fontSize = 1.1.rem
                fontWeight = FontWeight.w500
                letterSpacing = 1.px
                textAlign = TextAlign.center
                borderRadius = 30.px
                textDecoration = TextDecoration.none
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            rule(".neon-btn") {
                color = Color("var(--cyber-cyan)")
                backgroundColor = Color("var(--base)")
                border = Border(2.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
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
                background = "linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent)"
                zIndex = -1
                transition = Transitions().apply {
                    this += Transition("left", 0.5.s, Timing.ease)
                }
            }

            rule(".neon-btn:hover") {
                color = Color("var(--neon-pink)")
                backgroundColor = Color("var(--base)")
                borderColor = Color("var(--neon-pink)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--neon-pink)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
                }
                transform {
                    translateY((-5).px)
                }
            }
            
            rule(".neon-btn:hover::before") {
                left = 100.pct
            }

            // Footer
            footer {
                padding = Padding(20.px)
                textAlign = TextAlign.center
                backdropFilter = "blur(12px)"
                backgroundColor = Color("var(--surface)")
                borderTop = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
                }
                position = Position.relative
                zIndex = 10
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            // Floating terminal container
            rule(".terminal-container") {
                position = Position.relative
                width = 100.pct
                maxWidth = 800.px
                margin = Margin(2.rem, LinearDimension.auto)
                backdropFilter = "blur(12px)"
                backgroundColor = Color("var(--surface)")
                border = Border(2.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
                borderRadius = 10.px
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 20.px)
                }
                overflow = Overflow.hidden
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
                display = Display.block
            }

            // Skills matrix container
            rule(".skills-matrix") {
                position = Position.relative
                width = 100.pct
                height = 500.px
                margin = Margin(50.px, 0.px)
                border = Border(2.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
                backgroundColor = Color("var(--surface)")
                borderRadius = 10.px
                overflow = Overflow.hidden
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 20.px)
                }
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }
            
            // Section styling
            rule("section") {
                marginBottom = 60.px
            }
            
            rule("section h2") {
                fontSize = 2.5.rem
                marginBottom = 20.px
                position = Position.relative
                display = Display.inlineBlock
            }
            
            rule("section h2::after") {
                content = QuotedString("")
                position = Position.absolute
                bottom = (-10).px
                left = 0.px
                width = 100.pct
                height = 3.px
                background = "linear-gradient(90deg, var(--cyber-cyan), var(--neon-pink))"
                borderRadius = 3.px
            }

            // Content divider
            rule(".content-divider") {
                width = 100.pct
                height = 2.px
                margin = Margin(40.px, 0.px)
                position = Position.relative
                background = "linear-gradient(90deg, transparent, var(--cyber-cyan), var(--neon-pink), transparent)"
            }

            // Holographic Menu
            rule(".holographic-menu") {
                position = Position.fixed
                bottom = 30.px
                left = 50.pct
                transform {
                    translateX((-50).pct)
                }
                display = Display.flex
                gap = 20.px
                zIndex = 1000
                backdropFilter = "blur(12px)"
                backgroundColor = Color("var(--surface)")
                padding = Padding(12.px, 25.px)
                borderRadius = 40.px
                border = Border(2.px, BorderStyle.solid, Color("var(--glass-border)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 20.px)
                }
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            rule(".menu-icon") {
                width = 45.px
                height = 45.px
                borderRadius = 50.pct
                display = Display.flex
                alignItems = Align.center
                justifyContent = JustifyContent.center
                backgroundColor = Color("var(--base)")
                border = Border(2.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("rgba(var(--cyber-cyan-rgb), 0.5)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 10.px)
                }
                cursor = Cursor.pointer
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }
            
            rule(".menu-icon svg") {
                width = 24.px
                height = 24.px
                put("stroke", "var(--cyber-cyan)")
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }

            rule(".menu-icon:hover") {
                backgroundColor = Color("rgba(0, 255, 255, 0.2)")
                borderColor = Color("var(--neon-pink)")
                transform {
                    translateY((-8).px)
                    rotate(5.deg)
                }
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--neon-pink)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
                }
            }
            
            rule(".menu-icon:hover svg") {
                put("stroke", "var(--neon-pink)")
            }
            
            // Contact section
            rule(".contact-container") {
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = 20.px
                maxWidth = 600.px
                margin = Margin(30.px, LinearDimension.auto)
                padding = Padding(30.px)
            }
            
            rule(".contact-item") {
                display = Display.flex
                alignItems = Align.center
                padding = Padding(15.px)
                backgroundColor = Color("var(--surface)")
                borderRadius = 10.px
                border = Border(2.px, BorderStyle.solid, Color("rgba(var(--cyber-cyan-rgb), 0.5)"))
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }
            
            rule(".contact-item:hover") {
                transform {
                    translateX(10.px)
                }
                borderColor = Color("var(--neon-pink)")
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--neon-pink)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 15.px)
                }
            }
            
            rule(".contact-icon") {
                fontSize = 1.8.rem
                marginRight = 15.px
            }
            
            rule(".contact-item a") {
                color = Color("var(--cyber-cyan)")
                textDecoration = TextDecoration.none
                fontSize = 1.1.rem
                transition = Transitions().apply {
                    this += Transition("all", 0.3.s, Timing.ease)
                }
            }
            
            rule(".contact-item:hover a") {
                color = Color("var(--neon-pink)")
            }

            // Terminal styles (Internal layout)
            rule(".terminal") {
                fontFamily = "'Space Grotesk', monospace"
                color = Color("var(--neon-green)")
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("rgba(0, 255, 0, 0.5)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)
                }
                height = 100.pct
                display = Display.flex
                flexDirection = FlexDirection.column
            }

            rule(".terminal-header") {
                backgroundColor = Color("rgba(0, 255, 255, 0.1)")
                padding = Padding(0.5.rem, 1.rem)
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
                borderBottom = Border(1.px, BorderStyle.solid, Color("var(--cyber-cyan)"))
            }

            rule(".terminal-title") {
                fontSize = 0.9.rem
                color = Color("var(--cyber-cyan)")
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("rgba(0, 255, 255, 0.5)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)
                }
            }

            rule(".terminal-controls") {
                display = Display.flex
                gap = 0.5.rem
            }

            rule(".terminal-control") {
                width = 12.px
                height = 12.px
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
                height = 400.px
                overflowY = Overflow.auto
            }

            rule(".terminal-line") {
                marginBottom = 0.5.rem
                display = Display.flex
                alignItems = Align.flexStart
                gap = 0.5.rem
            }

            rule(".prompt") {
                color = Color("var(--neon-green)")
                fontWeight = FontWeight.bold
            }

            rule(".command") {
                color = Color("var(--cyber-cyan)")
            }

            rule(".output") {
                marginTop = 0.5.rem
                color = Color("var(--text)")
                lineHeight = LineHeight("1.5")
            }

            rule(".terminal-input-line") {
                display = Display.flex
                alignItems = Align.center
                gap = 0.5.rem
                padding = Padding(0.5.rem)
                backgroundColor = Color("rgba(0, 255, 255, 0.05)")
            }

            rule(".terminal-input") {
                background = "transparent"
                border = Border.none
                color = Color("var(--neon-green)")
                fontFamily = "'Space Grotesk', monospace"
                fontSize = 1.rem
                flex = Flex(1)
                outline = Outline.none
            }

            rule(".terminal-input::placeholder") {
                color = Color("rgba(0, 255, 0, 0.5)")
            }

            // Terminal scrollbar styling
            rule(".terminal-content::-webkit-scrollbar") {
                width = 8.px
            }

            rule(".terminal-content::-webkit-scrollbar-track") {
                backgroundColor = Color("rgba(0, 255, 255, 0.1)")
            }

            rule(".terminal-content::-webkit-scrollbar-thumb") {
                backgroundColor = Color("rgba(0, 255, 255, 0.3)")
                borderRadius = 4.px
            }

            rule(".terminal-content::-webkit-scrollbar-thumb:hover") {
                backgroundColor = Color("rgba(0, 255, 255, 0.5)")
            }

            // Media queries for responsive design
            media("(max-width: 768px)") {
                rule(".projects-container") {
                    gridTemplateColumns = GridTemplateColumns("1fr")
                }

                rule(".holographic-menu .menu-icon") {
                    width = 56.px
                    height = 56.px
                }
                
                rule(".theme-toggle") {
                    top = 80.px
                }
                
                rule("header") {
                    flexDirection = FlexDirection.column
                    alignItems = Align.center
                    padding = Padding(20.px, 10.px)
                    gap = 15.px
                }
            }
            
            // Low performance device optimizations
            rule(".low-performance .glass-morphic") {
                backdropFilter = "blur(5px)"  // Less intense blur
                boxShadow = BoxShadows().apply {
                    this += BoxShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)  // Smaller shadow
                }
            }
            
            rule(".low-performance #bg-canvas") {
                opacity = 0.7  // Less intense background
            }
            
            rule(".low-performance .glowing-text") {
                textShadow = TextShadows().apply {
                    this += TextShadow(color = Color("var(--cyber-cyan)"), offsetX = 0.px, offsetY = 0.px, blurRadius = 5.px)  // Less intense glow
                }
            }
            
            // Option to disable 3D effects entirely
            rule(".no-3d #bg-canvas, .no-3d .terminal-container, .no-3d .model-container") {
                display = Display.none
            }
            
            rule(".no-3d .skills-matrix") {
                height = LinearDimension.auto
                minHeight = 200.px
            }

        }.toString()
    }
}