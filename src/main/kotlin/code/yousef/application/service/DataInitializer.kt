package code.yousef.application.service

import code.yousef.infrastructure.persistence.entity.UserRole
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
//        initBlogPosts()

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

//    private fun initBlogPosts() {
//        try {
//            val blogCount = blogService.getAllBlogPosts().size
//            if (blogCount == 0) {
//                logger.info("Creating blog posts...")
//
//                for (i in 1..10) {
//                    val blogPost = BlogPost().apply {
//                        title = "Blog Post $i"
//                        summary = "This is a summary of blog post $i, showing a brief overview of the content."
//                        content = """
//                            # Blog Post $i
//
//                            This is the content of blog post $i. It contains multiple paragraphs of placeholder text.
//
//                            ## Section 1
//
//                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, nisl eget ultricies tincidunt.
//
//                            ## Section 2
//
//                            Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.
//                        """.trimIndent()
//                        imageUrl = "/images/blog$i.jpg"
//                        tags = listOf("Kotlin", "Web Development", "Tutorial", "Tag $i")
//                        createdAt = LocalDateTime.now().minusDays((10 - i).toLong())
//                        updatedAt = LocalDateTime.now().minusDays((10 - i).toLong())
//                        publishDate = LocalDateTime.now().minusDays((10 - i).toLong())
//                        published = true
//                        slug = "blog-post-$i"
//                    }
//                    blogService.createBlogPost(blogPost)
//                }
//            }
//        } catch (e: Exception) {
//            logger.error("Error initializing blog posts", e)
//        }
//    }
}