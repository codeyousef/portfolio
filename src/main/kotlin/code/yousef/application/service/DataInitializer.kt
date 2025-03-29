package code.yousef.application.service

import code.yousef.infrastructure.persistence.entity.UserRole
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.request.CreateUpdateUserRequest
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import org.jboss.logging.Logger

@ApplicationScoped
class DataInitializer @Inject constructor(
    private val userService: UserService,
    private val blogService: BlogService,
    private val projectService: ProjectService
) {
    private val logger = Logger.getLogger(DataInitializer::class.java)

    suspend fun init(@Observes event: StartupEvent) {
        logger.info("Initializing database with placeholder data...")

        // Initialize data in sequence
        initUsers()
        initProjects()
        initBlogPosts()

        logger.info("Database initialization completed")
    }

    private suspend fun initUsers() {
        try {
            val userCount = userService.getAllUsers().size
            if (userCount == 0) {
                logger.info("Creating users...")

                // Create admin user
                val adminRequest = CreateUpdateUserRequest(
                    username = "admin",
                    password = "admin",
                    name = "Administrator",
                    email = "admin@example.com",
                    role = UserRole.ADMIN
                )
                userService.createUser(adminRequest)

                // Create regular user
                val userRequest = CreateUpdateUserRequest(
                    username = "user",
                    password = "user",
                    name = "Regular User",
                    email = "user@example.com",
                    role = UserRole.CONTRIBUTOR
                )
                userService.createUser(userRequest)
            }
        } catch (e: Exception) {
            logger.error("Error initializing users", e)
        }
    }

    private suspend fun initProjects() {
        try {
            val projectCount = projectService.getAllProjects().size
            if (projectCount == 0) {
                logger.info("Creating projects...")

                for (i in 1..5) {
                    val projectRequest = CreateUpdateProjectRequest(
                        title = "Project $i",
                        description = "This is a sample project $i that showcases various skills and technologies.",
                        imageUrl = "/images/project$i.jpg",
                        modelUrl = "/models/project$i.glb",
                        technologies = listOf("Kotlin", "JavaScript", "Three.js", "WebGL"),
                        githubUrl = "https://github.com/yousef/project$i",
                        demoUrl = "https://demo.example.com/project$i",
                        featured = i <= 3
                    )
                    projectService.createProject(projectRequest)
                }
            }
        } catch (e: Exception) {
            logger.error("Error initializing projects", e)
        }
    }

    private suspend fun initBlogPosts() {
        try {
            val blogs = blogService.getAllBlogs()
            if (blogs.isEmpty()) {
                logger.info("Creating blog posts...")

                val blogTopics = listOf(
                    "Getting Started with Quarkus",
                    "Building Reactive Applications with Kotlin",
                    "Three.js Portfolio Design Guide",
                    "Modern Web Development Techniques",
                    "Implementing JWT Authentication in Quarkus"
                )

                for (i in blogTopics.indices) {
                    val title = blogTopics[i]
                    val blogRequest = CreateUpdateBlogRequest(
                        title = title,
                        summary = "A comprehensive guide to ${title.lowercase()}. Learn the fundamentals and best practices.",
                        content = """
                            # $title
                            
                            This is the content of the blog post about ${title.lowercase()}. It contains multiple paragraphs of information.
                            
                            ## Introduction
                            
                            The introduction covers the basic concepts and explains why this topic is important.
                            
                            ## Main Concepts
                            
                            This section goes into detail about the main concepts and techniques.
                            
                            ## Code Examples
                            
                            ```kotlin
                            fun example() {
                                println("This is a code example for $title")
                            }
                            ```
                            
                            ## Conclusion
                            
                            Summary of what we've learned and next steps.
                        """.trimIndent(),
                        imageUrl = "/images/blog${i + 1}.jpg",
                        tags = listOf("Kotlin", "Web Development", "Tutorial", "Quarkus"),
                        published = i < 4, // First 4 are published
                        slug = null // Let it be auto-generated
                    )
                    blogService.createBlog(blogRequest)
                }
            }
        } catch (e: Exception) {
            logger.error("Error initializing blog posts", e)
        }
    }
}