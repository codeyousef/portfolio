package code.yousef.ui

import code.yousef.summon.runtime.Composable
import code.yousef.summon.components.display.Text
import code.yousef.summon.components.input.Button
import code.yousef.summon.components.layout.Box
import code.yousef.summon.components.layout.Column
import code.yousef.summon.components.layout.Row
import code.yousef.summon.modifier.*

@Composable
fun HomePage() {
    // Main container with background styling
    Box(
        modifier = Modifier()
            .style("style", """
                min-height: 100vh;
                background-color: #0a0a14;
                position: relative;
                overflow: hidden;
                font-family: 'Space Grotesk', 'Inter', sans-serif;
            """)
    ) {
        // Background gradient effects
        Box(
            modifier = Modifier()
                .style("style", """
                    position: absolute;
                    top: -300px;
                    right: -300px;
                    width: 600px;
                    height: 600px;
                    border-radius: 50%;
                    background: radial-gradient(circle, rgba(0, 247, 255, 0.15) 0%, rgba(0, 247, 255, 0) 70%);
                    z-index: 0;
                """)
        ) {}

        Box(
            modifier = Modifier()
                .style("style", """
                    position: absolute;
                    bottom: -200px;
                    left: -200px;
                    width: 500px;
                    height: 500px;
                    border-radius: 50%;
                    background: radial-gradient(circle, rgba(255, 42, 109, 0.1) 0%, rgba(255, 42, 109, 0) 70%);
                    z-index: 0;
                """)
        ) {}

        // Main content container
        Column(
            modifier = Modifier()
                .style("style", """
                    max-width: 1200px;
                    margin: 0 auto;
                    padding: 2rem;
                    position: relative;
                    z-index: 1;
                """)
        ) {
            // Header/Logo
            Box(
                modifier = Modifier()
                    .style("style", """
                        margin-bottom: 3rem;
                        display: flex;
                        justify-content: center;
                    """)
            ) {
                Text(
                    text = "NEOTECH",
                    modifier = Modifier()
                        .style("style", """
                            font-family: 'Syne', sans-serif;
                            font-size: 2.5rem;
                            font-weight: 700;
                            color: #ffffff;
                            letter-spacing: 2px;
                            position: relative;
                            display: inline-block;
                        """)
                )
            }

            // Hero section
            Box(
                modifier = Modifier()
                    .style("style", """
                        margin-bottom: 4rem;
                        text-align: center;
                    """)
            ) {
                Column {
                    Text(
                        text = "Crafting Digital Experiences With Cutting-Edge Tech",
                        modifier = Modifier()
                            .style("style", """
                                font-family: 'Syne', sans-serif;
                                font-size: 3rem;
                                font-weight: 700;
                                margin-bottom: 1.5rem;
                                background: linear-gradient(90deg, #00f7ff, #ff2a6d);
                                -webkit-background-clip: text;
                                background-clip: text;
                                color: transparent;
                                line-height: 1.2;
                            """)
                    )

                    Text(
                        text = "Software developer specializing in Kotlin, Quarkus, and modern web technologies. Let's build the future together.",
                        modifier = Modifier()
                            .style("style", """
                                font-size: 1.25rem;
                                color: rgba(255, 255, 255, 0.7);
                                max-width: 800px;
                                margin: 0 auto 2rem auto;
                            """)
                    )

                    Row(
                        modifier = Modifier()
                            .style("style", """
                                display: flex;
                                gap: 1rem;
                                justify-content: center;
                                flex-wrap: wrap;
                            """)
                    ) {
                        Button(
                            label = "View Projects",
                            modifier = Modifier()
                                .style("style", """
                                    background: #00f7ff;
                                    color: #0a0a14;
                                    font-family: 'Space Grotesk', sans-serif;
                                    font-weight: 600;
                                    padding: 0.75rem 1.5rem;
                                    border-radius: 0.5rem;
                                    border: none;
                                    cursor: pointer;
                                    transition: all 0.3s ease;
                                    text-transform: uppercase;
                                    letter-spacing: 1px;
                                    box-shadow: 0 0 20px rgba(0, 247, 255, 0.3);
                                """)
                                .attribute("onmouseover", "this.style.transform='translateY(-3px)'; this.style.boxShadow='0 0 30px rgba(0, 247, 255, 0.5)';")
                                .attribute("onmouseout", "this.style.transform='translateY(0)'; this.style.boxShadow='0 0 20px rgba(0, 247, 255, 0.3)';")
                                .attribute("onclick", "window.location.href='#projects'"),
                            onClick = {}
                        )

                        Button(
                            label = "Contact Me",
                            modifier = Modifier()
                                .style("style", """
                                    background: transparent;
                                    color: #00f7ff;
                                    font-family: 'Space Grotesk', sans-serif;
                                    font-weight: 600;
                                    padding: 0.75rem 1.5rem;
                                    border-radius: 0.5rem;
                                    border: 2px solid #00f7ff;
                                    cursor: pointer;
                                    transition: all 0.3s ease;
                                    text-transform: uppercase;
                                    letter-spacing: 1px;
                                """)
                                .attribute("onmouseover", "this.style.background='rgba(0, 247, 255, 0.1)'; this.style.transform='translateY(-3px)';")
                                .attribute("onmouseout", "this.style.background='transparent'; this.style.transform='translateY(0)';")
                                .attribute("onclick", "window.location.href='#contact'"),
                            onClick = {}
                        )
                    }
                }
            }

            // Featured sections grid
            Text(
                text = "EXPLORE MY WORK",
                modifier = Modifier()
                    .style("style", """
                        font-family: 'Syne', sans-serif;
                        font-size: 1.5rem;
                        font-weight: 700;
                        margin-bottom: 2rem;
                        color: #ffffff;
                        text-align: center;
                        letter-spacing: 2px;
                    """)
            )

            // Grid layout for featured sections
            Row(
                modifier = Modifier()
                    .style("style", """
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
                        gap: 2rem;
                        margin-bottom: 4rem;
                    """)
            ) {
                // Projects section
                Box(
                    modifier = Modifier()
                        .style("style", """
                            background: rgba(16, 16, 30, 0.7);
                            backdrop-filter: blur(10px);
                            border-radius: 1rem;
                            padding: 2rem;
                            border: 1px solid rgba(0, 247, 255, 0.1);
                            transition: all 0.3s ease;
                            position: relative;
                            overflow: hidden;
                        """)
                        .attribute("onmouseover", "this.style.transform='translateY(-10px)'; this.style.boxShadow='0 10px 30px rgba(0, 247, 255, 0.1)'; this.style.borderColor='rgba(0, 247, 255, 0.3)';")
                        .attribute("onmouseout", "this.style.transform='translateY(0)'; this.style.boxShadow='none'; this.style.borderColor='rgba(0, 247, 255, 0.1)';")
                ) {
                    // Glow effect
                    Box(
                        modifier = Modifier()
                            .style("style", """
                                position: absolute;
                                top: -50px;
                                right: -50px;
                                width: 100px;
                                height: 100px;
                                border-radius: 50%;
                                background: radial-gradient(circle, rgba(0, 247, 255, 0.2) 0%, rgba(0, 247, 255, 0) 70%);
                                z-index: 0;
                            """)
                    ) {}

                    Column(
                        modifier = Modifier()
                            .style("style", "position: relative; z-index: 1;")
                    ) {
                        Text(
                            text = "Projects",
                            modifier = Modifier()
                                .style("style", """
                                    font-family: 'Syne', sans-serif;
                                    font-size: 1.5rem;
                                    font-weight: 700;
                                    margin-bottom: 1rem;
                                    color: #00f7ff;
                                """)
                        )

                        Text(
                            text = "Explore my latest work showcasing innovative solutions and cutting-edge technologies.",
                            modifier = Modifier()
                                .style("style", """
                                    color: rgba(255, 255, 255, 0.7);
                                    margin-bottom: 1.5rem;
                                """)
                        )

                        Button(
                            label = "View Projects",
                            modifier = Modifier()
                                .style("style", """
                                    background: transparent;
                                    color: #00f7ff;
                                    font-family: 'Space Grotesk', sans-serif;
                                    font-weight: 500;
                                    padding: 0.5rem 1rem;
                                    border-radius: 0.5rem;
                                    border: 1px solid #00f7ff;
                                    cursor: pointer;
                                    transition: all 0.3s ease;
                                """)
                                .attribute("onmouseover", "this.style.background='rgba(0, 247, 255, 0.1)';")
                                .attribute("onmouseout", "this.style.background='transparent';")
                                .attribute("onclick", "window.location.href='#projects'"),
                            onClick = {}
                        )
                    }
                }

                // Skills section
                Box(
                    modifier = Modifier()
                        .style("style", """
                            background: rgba(16, 16, 30, 0.7);
                            backdrop-filter: blur(10px);
                            border-radius: 1rem;
                            padding: 2rem;
                            border: 1px solid rgba(255, 42, 109, 0.1);
                            transition: all 0.3s ease;
                            position: relative;
                            overflow: hidden;
                        """)
                        .attribute("onmouseover", "this.style.transform='translateY(-10px)'; this.style.boxShadow='0 10px 30px rgba(255, 42, 109, 0.1)'; this.style.borderColor='rgba(255, 42, 109, 0.3)';")
                        .attribute("onmouseout", "this.style.transform='translateY(0)'; this.style.boxShadow='none'; this.style.borderColor='rgba(255, 42, 109, 0.1)';")
                ) {
                    // Glow effect
                    Box(
                        modifier = Modifier()
                            .style("style", """
                                position: absolute;
                                top: -50px;
                                right: -50px;
                                width: 100px;
                                height: 100px;
                                border-radius: 50%;
                                background: radial-gradient(circle, rgba(255, 42, 109, 0.2) 0%, rgba(255, 42, 109, 0) 70%);
                                z-index: 0;
                            """)
                    ) {}

                    Column(
                        modifier = Modifier()
                            .style("style", "position: relative; z-index: 1;")
                    ) {
                        Text(
                            text = "Skills",
                            modifier = Modifier()
                                .style("style", """
                                    font-family: 'Syne', sans-serif;
                                    font-size: 1.5rem;
                                    font-weight: 700;
                                    margin-bottom: 1rem;
                                    color: #ff2a6d;
                                """)
                        )

                        Text(
                            text = "Advanced technologies powering my development stack and expertise.",
                            modifier = Modifier()
                                .style("style", """
                                    color: rgba(255, 255, 255, 0.7);
                                    margin-bottom: 1.5rem;
                                """)
                        )

                        Button(
                            label = "View Skills",
                            modifier = Modifier()
                                .style("style", """
                                    background: transparent;
                                    color: #ff2a6d;
                                    font-family: 'Space Grotesk', sans-serif;
                                    font-weight: 500;
                                    padding: 0.5rem 1rem;
                                    border-radius: 0.5rem;
                                    border: 1px solid #ff2a6d;
                                    cursor: pointer;
                                    transition: all 0.3s ease;
                                """)
                                .attribute("onmouseover", "this.style.background='rgba(255, 42, 109, 0.1)';")
                                .attribute("onmouseout", "this.style.background='transparent';")
                                .attribute("onclick", "window.location.href='#skills'"),
                            onClick = {}
                        )
                    }
                }

                // Blog section
                Box(
                    modifier = Modifier()
                        .style("style", """
                            background: rgba(16, 16, 30, 0.7);
                            backdrop-filter: blur(10px);
                            border-radius: 1rem;
                            padding: 2rem;
                            border: 1px solid rgba(5, 255, 161, 0.1);
                            transition: all 0.3s ease;
                            position: relative;
                            overflow: hidden;
                        """)
                        .attribute("onmouseover", "this.style.transform='translateY(-10px)'; this.style.boxShadow='0 10px 30px rgba(5, 255, 161, 0.1)'; this.style.borderColor='rgba(5, 255, 161, 0.3)';")
                        .attribute("onmouseout", "this.style.transform='translateY(0)'; this.style.boxShadow='none'; this.style.borderColor='rgba(5, 255, 161, 0.1)';")
                ) {
                    // Glow effect
                    Box(
                        modifier = Modifier()
                            .style("style", """
                                position: absolute;
                                top: -50px;
                                right: -50px;
                                width: 100px;
                                height: 100px;
                                border-radius: 50%;
                                background: radial-gradient(circle, rgba(5, 255, 161, 0.2) 0%, rgba(5, 255, 161, 0) 70%);
                                z-index: 0;
                            """)
                    ) {}

                    Column(
                        modifier = Modifier()
                            .style("style", "position: relative; z-index: 1;")
                    ) {
                        Text(
                            text = "Blog",
                            modifier = Modifier()
                                .style("style", """
                                    font-family: 'Syne', sans-serif;
                                    font-size: 1.5rem;
                                    font-weight: 700;
                                    margin-bottom: 1rem;
                                    color: #05ffa1;
                                """)
                        )

                        Text(
                            text = "Read my latest articles and insights on technology and development.",
                            modifier = Modifier()
                                .style("style", """
                                    color: rgba(255, 255, 255, 0.7);
                                    margin-bottom: 1.5rem;
                                """)
                        )

                        Button(
                            label = "Read Blog",
                            modifier = Modifier()
                                .style("style", """
                                    background: transparent;
                                    color: #05ffa1;
                                    font-family: 'Space Grotesk', sans-serif;
                                    font-weight: 500;
                                    padding: 0.5rem 1rem;
                                    border-radius: 0.5rem;
                                    border: 1px solid #05ffa1;
                                    cursor: pointer;
                                    transition: all 0.3s ease;
                                """)
                                .attribute("onmouseover", "this.style.background='rgba(5, 255, 161, 0.1)';")
                                .attribute("onmouseout", "this.style.background='transparent';")
                                .attribute("onclick", "window.location.href='/blog'"),
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}
