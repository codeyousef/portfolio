package code.yousef.application.service

import code.yousef.infrastructure.persistence.entity.BlogPost
import code.yousef.infrastructure.persistence.entity.Project
import code.yousef.infrastructure.persistence.entity.UserEntity
import code.yousef.infrastructure.persistence.entity.UserRole
import code.yousef.domain.repository.BlogPostRepo
import code.yousef.domain.repository.ProjectRepo
import code.yousef.domain.repository.UserRepo
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.jboss.logging.Logger
import java.time.LocalDateTime

@ApplicationScoped
class DataInitializer @Inject constructor(
    var userService: UserService,
    var blogService: BlogService,
    var projectService: ProjectService,
    var userRepo: UserRepo,
    var blogPostRepo: BlogPostRepo,
    var projectRepo: ProjectRepo,
    val sessionFactory: SessionFactory
) {

    private val logger = Logger.getLogger(DataInitializer::class.java)

    fun init(@Observes event: StartupEvent) {
        logger.info("Initializing database with placeholder data...")


        // Use blocking operations for initialization
        initUsers()
        initProjects()
        initBlogPosts()


        logger.info("Database initialization completed")
    }

    private fun initUsers() {
        var  userCount:Int? = null
        sessionFactory.withSession {
            userCount = userService.getAllUsers().await().indefinitely().size
        }
        if (userCount == 0) {
            logger.info("Creating users...")

            val adminUserEntity = UserEntity().apply {
                username = "admin"
                password = "admin"
                name = "Administrator"
                email = "admin@example.com"
                role = UserRole.ADMIN
                createdAt = LocalDateTime.now()
                updatedAt = LocalDateTime.now()
            }

            val regularUserEntity = UserEntity().apply {
                username = "user"
                password = "user"
                name = "Regular User"
                email = "user@example.com"
                role = UserRole.CONTRIBUTOR
                createdAt = LocalDateTime.now()
                updatedAt = LocalDateTime.now()
            }
            sessionFactory.withSession { session ->
                session.withTransaction {
                    userService.createUser(adminUserEntity)
                    userService.createUser(regularUserEntity)
                }
            }
        }
    }

    private fun initProjects() {
        val projectCount = projectService.getAllProjects().await().indefinitely().size
        if (projectCount == 0) {
            logger.info("Creating projects...")

            for (i in 1..5) {
                val project = Project().apply {
                    title = "Project $i"
                    description = "This is a sample project $i that showcases various skills and technologies."
                    imageUrl = "/images/project$i.jpg"
                    modelUrl = "/models/project$i.glb"
                    technologies = listOf("Kotlin", "JavaScript", "Three.js", "WebGL")
                    githubUrl = "https://github.com/yousef/project$i"
                    demoUrl = "https://demo.example.com/project$i"
                    featured = i <= 3
                    createdAt = LocalDateTime.now()
                    updatedAt = LocalDateTime.now()
                }
                sessionFactory.withSession { session ->
                    session.withTransaction {
                        projectService.createProject(project)
                    }
                }
            }
        }
    }

    private fun initBlogPosts() {
        val blogCount = blogService.getAllBlogPosts().await().indefinitely().size
        if (blogCount == 0) {
            logger.info("Creating blog posts...")

            for (i in 1..10) {
                val blogPost = BlogPost().apply {
                    title = "Blog Post $i"
                    summary = "This is a summary of blog post $i, showing a brief overview of the content."
                    content = """
                        # Blog Post $i
                        
                        This is the content of blog post $i. It contains multiple paragraphs of placeholder text.
                        
                        ## Section 1
                        
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam auctor, nisl eget ultricies tincidunt.
                        
                        ## Section 2
                        
                        Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.
                    """.trimIndent()
                    imageUrl = "/images/blog$i.jpg"
                    tags = listOf("Kotlin", "Web Development", "Tutorial", "Tag $i")
                    createdAt = LocalDateTime.now().minusDays((10 - i).toLong())
                    updatedAt = LocalDateTime.now().minusDays((10 - i).toLong())
                    publishDate = LocalDateTime.now().minusDays((10 - i).toLong())
                    published = true
                    slug = "blog-post-$i"
                }
                sessionFactory.withSession { session ->
                    session.withTransaction {
                        blogService.createBlogPost(blogPost)
                    }
                }
            }
        }
    }
}