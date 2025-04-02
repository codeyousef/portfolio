package code.yousef

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.id
import kotlinx.html.p
import kotlinx.html.section
import kotlinx.html.style
import code.yousef.model.Project

fun main() {
    window.onload = {
        val root = document.getElementById("root") ?: document.body!!
        
        root.append {
            section {
                style = "padding: 20px; max-width: 1200px; margin: 0 auto;"
                
                div {
                    id = "header"
                    style = "background-color: #333; color: white; padding: 20px; border-radius: 5px; margin-bottom: 20px;"
                    
                    h1 {
                        +"Portfolio Frontend (Kotlin/JS)"
                    }
                    
                    p {
                        +"This is the Kotlin/JS frontend module that loads data from the Quarkus backend"
                    }
                }
                
                div {
                    id = "projects"
                    style = "margin-top: 30px;"
                    
                    h2 {
                        +"Projects"
                    }
                    
                    // Create a sample project to demonstrate the shared model
                    val project = Project(
                        id = "sample-1",
                        title = "Sample Project",
                        description = "This is a sample project to demonstrate shared models",
                        imageUrl = "/images/sample.jpg",
                        technologies = listOf("Kotlin", "Quarkus", "Kotlin/JS")
                    )
                    
                    // Display the project information
                    div {
                        style = "background-color: #f5f5f5; padding: 16px; border-radius: 4px; margin-top: 16px;"
                        h2 { +project.title }
                        p { +project.description }
                        if (project.technologies.isNotEmpty()) {
                            p { +"Technologies: ${project.technologies.joinToString(", ")}" }
                        }
                    }
                    
                    p {
                        +"In a real app, we would fetch projects from the API endpoint /api/v1/projects"
                    }
                }
            }
        }
    }
} 