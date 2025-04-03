package code.yousef.pages

import code.yousef.components.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlinx.html.js.onClickFunction
import org.w3c.dom.Element

fun homePage(): Element {
    return document.create.div {
        heroSection()

        // Projects section
        section(id = "projects") {
            container {
                sectionHeader("My Projects", "Check out some of my recent works")

                div {
                    classes = setOf("project-grid")

                    // Project cards
                    projectCard(
                        image = "img/projects/project1.jpg",
                        title = "E-Commerce Platform",
                        description = "A full-featured online shopping platform with cart, checkout, and payment integration.",
                        technologies = listOf("Java", "Spring Boot", "React", "PostgreSQL"),
                        liveLink = "#",
                        repoLink = "#"
                    )

                    projectCard(
                        image = "img/projects/project2.jpg",
                        title = "Task Management App",
                        description = "A productivity application for managing tasks, with real-time updates and team collaboration.",
                        technologies = listOf("Kotlin", "Ktor", "Vue.js", "MongoDB"),
                        liveLink = "#",
                        repoLink = "#"
                    )

                    projectCard(
                        image = "img/projects/project3.jpg",
                        title = "Financial Dashboard",
                        description = "Interactive data visualization dashboard for financial analytics with real-time data.",
                        technologies = listOf("Python", "FastAPI", "D3.js", "MySQL"),
                        liveLink = "#",
                        repoLink = "#"
                    )

                    projectCard(
                        image = "img/projects/project4.jpg",
                        title = "Social Media Platform",
                        description = "A community platform with profiles, posts, comments, and real-time messaging.",
                        technologies = listOf("Node.js", "Express", "React", "Socket.io", "MongoDB"),
                        liveLink = "#",
                        repoLink = "#"
                    )
                }

                button {
                    classes = setOf("btn-primary", "view-all")
                    +"View All Projects"
                    onClickFunction = {
                        window.location.href = "/projects"
                    }
                }
            }
        }

        // Services section
        section(id = "services") {
            container {
                sectionHeader("My Services", "Here's how I can help you")

                div {
                    classes = setOf("services-grid")

                    serviceCard(
                        icon = "code",
                        title = "Web Development",
                        description = "Full-stack web development with modern frameworks and technologies."
                    )

                    serviceCard(
                        icon = "mobile",
                        title = "Mobile Apps",
                        description = "Native and cross-platform mobile applications for iOS and Android."
                    )

                    serviceCard(
                        icon = "database",
                        title = "Database Design",
                        description = "Efficient database architecture and optimization for performance."
                    )

                    serviceCard(
                        icon = "cloud",
                        title = "Cloud Solutions",
                        description = "Deployment and management of applications on cloud platforms."
                    )

                    serviceCard(
                        icon = "shield",
                        title = "Security",
                        description = "Implementation of best security practices and vulnerability assessment."
                    )

                    serviceCard(
                        icon = "chart",
                        title = "Performance Optimization",
                        description = "Improving application speed and resource utilization."
                    )
                }
            }
        }

        // About section
        section(id = "about") {
            container {
                sectionHeader("About Me", "Get to know me better")

                div {
                    classes = setOf("about-content")

                    div {
                        classes = setOf("about-image")
                        img {
                            src = "img/about/profile.jpg"
                            alt = "Profile"
                        }
                    }

                    div {
                        classes = setOf("about-text")
                        p {
                            +"I'm a passionate software developer with over 5 years of experience building web and mobile applications. I specialize in creating efficient, scalable, and user-friendly solutions that solve real-world problems."
                        }

                        p {
                            +"My expertise includes full-stack development, cloud infrastructure, and database design. I'm constantly learning new technologies and methodologies to stay at the forefront of the industry."
                        }

                        h3 {
                            +"Skills & Technologies"
                        }

                        ul {
                            classes = setOf("skills-list")
                            li { +"Java, Kotlin, JavaScript, TypeScript, Python" }
                            li { +"Spring Boot, Quarkus, React, Vue.js, Node.js" }
                            li { +"AWS, Google Cloud, Docker, Kubernetes" }
                            li { +"MySQL, PostgreSQL, MongoDB, Redis" }
                            li { +"CI/CD, Test Automation, Agile Methodologies" }
                        }

                        a {
                            href = "/resume"
                            classes = setOf("btn-secondary")
                            +"View Resume"
                        }
                    }
                }
            }
        }

        // Blog section preview
        section(id = "blog-preview") {
            container {
                sectionHeader("Latest Articles", "Read my thoughts on technology and development")

                div {
                    classes = setOf("blog-grid")

                    blogCard(
                        image = "img/blog/blog1.jpg",
                        title = "The Future of Web Development",
                        excerpt = "Exploring emerging technologies and trends shaping the future of web development.",
                        date = "March 15, 2023",
                        readTime = "5 min read",
                        link = "/blog/future-web-development"
                    )

                    blogCard(
                        image = "img/blog/blog2.jpg",
                        title = "Optimizing Database Performance",
                        excerpt = "Practical tips and techniques for improving database efficiency in high-load applications.",
                        date = "February 28, 2023",
                        readTime = "8 min read",
                        link = "/blog/database-optimization"
                    )

                    blogCard(
                        image = "img/blog/blog3.jpg",
                        title = "Building Accessible Web Applications",
                        excerpt = "Best practices for creating inclusive web experiences that work for everyone.",
                        date = "January 12, 2023",
                        readTime = "6 min read",
                        link = "/blog/web-accessibility"
                    )
                }

                a {
                    href = "/blog"
                    classes = setOf("btn-primary", "view-all")
                    +"View All Articles"
                }
            }
        }

        // Contact section
        section(id = "contact") {
            container {
                sectionHeader("Get In Touch", "Have a project in mind? Let's talk!")

                div {
                    classes = setOf("contact-container")

                    div {
                        classes = setOf("contact-info")

                        div {
                            classes = setOf("contact-item")
                            i { classes = setOf("icon", "email") }
                            p { +"hello@yousef.dev" }
                        }

                        div {
                            classes = setOf("contact-item")
                            i { classes = setOf("icon", "phone") }
                            p { +"+1 (555) 123-4567" }
                        }

                        div {
                            classes = setOf("contact-item")
                            i { classes = setOf("icon", "location") }
                            p { +"San Francisco, CA" }
                        }

                        div {
                            classes = setOf("social-links")
                            a {
                                href = "https://github.com"
                                target = "_blank"
                                rel = "noopener noreferrer"
                                i { classes = setOf("icon", "github") }
                            }

                            a {
                                href = "https://linkedin.com"
                                target = "_blank"
                                rel = "noopener noreferrer"
                                i { classes = setOf("icon", "linkedin") }
                            }

                            a {
                                href = "https://twitter.com"
                                target = "_blank"
                                rel = "noopener noreferrer"
                                i { classes = setOf("icon", "twitter") }
                            }
                        }
                    }

                    form {
                        classes = setOf("contact-form")

                        div {
                            classes = setOf("form-group")
                            label {
                                htmlFor = "name"
                                +"Name"
                            }
                            textInput {
                                id = "name"
                                name = "name"
                                required = true
                            }
                        }

                        div {
                            classes = setOf("form-group")
                            label {
                                htmlFor = "email"
                                +"Email"
                            }
                            emailInput {
                                id = "email"
                                name = "email"
                                required = true
                            }
                        }

                        div {
                            classes = setOf("form-group")
                            label {
                                htmlFor = "message"
                                +"Message"
                            }
                            textArea {
                                id = "message"
                                name = "message"
                                rows = "5"
                                required = true
                            }
                        }

                        button {
                            type = ButtonType.submit
                            classes = setOf("btn-primary")
                            +"Send Message"
                        }
                    }
                }
            }
        }
    }
}