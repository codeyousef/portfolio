package code.yousef.application.service

import code.yousef.infrastructure.persistence.entity.UserRole
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.request.CreateUpdateProjectRequest
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.request.CreateUpdateUserRequest
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.jboss.logging.Logger

@ApplicationScoped
class DataInitializer @Inject constructor(
    private val userService: UserService,
    private val blogService: BlogService,
    private val projectService: ProjectService,
    private val serviceService: ServiceService,
    val sessionFactory: SessionFactory
) {
    private val logger = Logger.getLogger(DataInitializer::class.java)

    fun init(@Observes event: StartupEvent) {
        logger.info("Initializing database with placeholder data...")

        // Use runBlocking to bridge non-suspending context to suspending functions
        runBlocking {
            try {
                // Initialize data in sequence
                initUsers()
                initProjects()
                initBlogPosts()
                initServices()

                logger.info("Database initialization completed")
            } catch (e: Exception) {
                logger.error("Error during database initialization", e)
            }
        }
    }

    private suspend fun initUsers() {
        try {
            val userCount = userService.getAllUsers().size
            if (userCount == 0) {
                logger.info("Creating users...")

                // Create admin user
                val adminRequest = CreateUpdateUserRequest(
                    username = "admin",
                    password = userService.hashPassword("admin"),
                    name = "Administrator",
                    email = "admin@example.com",
                    role = UserRole.ADMIN
                )
                userService.createUser(adminRequest)

                // Create regular user
                val userRequest = CreateUpdateUserRequest(
                    username = "user",
                    password = userService.hashPassword("user"),
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
                        imageUrl = "https://placehold.co/600x350/333/white?text=Project+$i",
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

    private suspend fun initServices() {
        try {
            val serviceCount = serviceService.getAllServices().size
            if (serviceCount == 0) {
                logger.info("Creating services...")

                val services = listOf(
                    CreateUpdateServiceRequest(
                        title = "Consultation",
                        shortDescription = "Expert guidance tailored to your business needs",
                        fullDescription = "Our consultation services provide strategic insights and expert advice to help navigate complex technical challenges. We offer personalized sessions focused on identifying optimal solutions for your specific business requirements.",
                        iconClass = "fa-comments",
                        displayOrder = 1,
                        featured = true,
                        ctaText = "Book Now",
                        ctaLink = "/?service=Consultation#contact",
                        price = "SAR 300",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "Web Design",
                        shortDescription = "Captivating designs that elevate your brand",
                        fullDescription = "Our web design services focus on creating visually stunning, user-centric interfaces that represent your brand identity. We combine aesthetics with functionality to deliver designs that engage visitors and support your business objectives.",
                        iconClass = "fa-palette",
                        displayOrder = 2,
                        featured = true,
                        ctaText = "Details",
                        ctaLink = "/?service=WebDesign#contact",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "Website Development",
                        shortDescription = "Impactful single-page websites with essential features",
                        fullDescription = "Perfect for landing pages, portfolios, or compact business presentations. Our single-page website development delivers a concise, engaging online presence optimized for conversions and user engagement.",
                        iconClass = "fa-file-code",
                        displayOrder = 3,
                        featured = true,
                        ctaText = "Details",
                        ctaLink = "/?service=WebDevelopment#contact",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "Mobile App Development",
                        shortDescription = "Native and cross-platform mobile applications",
                        fullDescription = "Create standalone mobile applications with intuitive interfaces and essential functionalities. Our development process focuses on optimized performance, responsive design, and excellent user experience across iOS and Android platforms.",
                        iconClass = "fa-mobile-alt",
                        displayOrder = 4,
                        featured = false,
                        ctaText = "Details",
                        ctaLink = "/?service=MobileDevelopment#contact",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "Web Scraping",
                        shortDescription = "Automated data extraction and processing solutions",
                        fullDescription = "Our web scraping services provide efficient, automated data collection from websites and online resources. We develop custom scrapers that extract, transform, and organize information according to your specific requirements, with respect for rate limits and site policies.",
                        iconClass = "fa-spider",
                        displayOrder = 5,
                        featured = false,
                        ctaText = "Details",
                        ctaLink = "/?service=WebScraping#contact",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "Automation",
                        shortDescription = "Workflow automation using n8n, Zapier, Make.com and Microsoft Power Automate",
                        fullDescription = "Streamline your business processes with our automation services utilizing industry-leading platforms like n8n, Zapier, Make.com, and Microsoft Power Automate. We create custom workflows that eliminate repetitive tasks, synchronize systems, and boost productivity.",
                        iconClass = "fa-robot",
                        displayOrder = 6,
                        featured = true,
                        ctaText = "Details",
                        ctaLink = "/?service=Automation#contact",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "AI Chatbot Development",
                        shortDescription = "Intelligent conversational interfaces for customer engagement",
                        fullDescription = "Custom AI chatbot solutions designed to enhance customer service, streamline inquiries, and provide instant assistance. Our chatbots feature natural language processing capabilities, continuous learning, and seamless integration with existing systems.",
                        iconClass = "fa-comment-dots",
                        displayOrder = 7,
                        featured = false,
                        ctaText = "Details",
                        ctaLink = "/?service=Chatbot#contact",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "AI Agent Development",
                        shortDescription = "Advanced AI solutions for complex task automation",
                        fullDescription = "Specialized AI agents designed to perform complex tasks, make decisions, and operate autonomously within defined parameters. Our development focuses on creating intelligent systems that can understand context, learn from interactions, and deliver consistent results.",
                        iconClass = "fa-brain",
                        displayOrder = 8,
                        featured = true,
                        ctaText = "Details",
                        ctaLink = "/?service=Agent#contact",
                        detailsLink = "/services"
                    ),
                    CreateUpdateServiceRequest(
                        title = "SharePoint Development",
                        shortDescription = "Custom SharePoint solutions for enhanced collaboration",
                        fullDescription = "Comprehensive SharePoint development services to optimize your organization's information sharing and team collaboration. We create custom workflows, applications, and integrations that extend SharePoint's capabilities to address your specific business requirements.",
                        iconClass = "fa-share-alt",
                        displayOrder = 9,
                        featured = false,
                        ctaText = "Details",
                        ctaLink = "/?service=SharePoint#contact",
                        detailsLink = "/services"
                    )
                )

                // Create services and collect their IDs
                val createdServices = services.map { request ->
                    serviceService.createService(request)
                }

                // Now update each service with its specific detailsLink
                for (i in createdServices.indices) {
                    val service = createdServices[i]
                    // Skip the first service (Consultation) as it doesn't need a details link
                    if (i > 0) {
                        logger.info("Updating service ${service.title} with ID ${service.id}")

                        val updateRequest = CreateUpdateServiceRequest(
                            title = service.title,
                            shortDescription = service.shortDescription,
                            fullDescription = service.fullDescription,
                            iconClass = service.iconClass,
                            displayOrder = service.displayOrder,
                            featured = service.featured,
                            ctaText = service.ctaText,
                            ctaLink = service.ctaLink,
                            detailsLink = "/services/${service.id}", // Set the details link
                            price = service.price
                        )

                        val updated = serviceService.updateService(service.id!!, updateRequest)
                        if (updated != null) {
                            logger.info("Updated service ${updated.title}, detailsLink is now: ${updated.detailsLink}")
                        }
                    }
                }

                // Verify the updates
                val finalServices = serviceService.getAllServices()
                finalServices.forEach { service ->
                    logger.info("Final check - Service '${service.title}' has detailsLink: ${service.detailsLink}")
                }
            }
        } catch (e: Exception) {
            logger.error("Error initializing services", e)
        }
    }

}