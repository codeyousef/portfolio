package code.yousef.infrastructure.startup

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import org.jboss.logging.Logger

/**
 * Listener for application startup events.
 * This class logs when the application starts up.
 */
@ApplicationScoped
class ApplicationStartupListener {
    private val logger = Logger.getLogger(ApplicationStartupListener::class.java)

    /**
     * Method called when the application starts up.
     * This is a good place to perform initialization tasks.
     */
    fun onStart(@Observes event: StartupEvent) {
        logger.info("=== APPLICATION STARTED ===")
        logger.info("ApplicationStartupListener.onStart() called - application has started")
        
        // Log some system information
        logger.info("Java version: ${System.getProperty("java.version")}")
        logger.info("OS: ${System.getProperty("os.name")} ${System.getProperty("os.version")}")
        logger.info("Available processors: ${Runtime.getRuntime().availableProcessors()}")
        logger.info("Max memory: ${Runtime.getRuntime().maxMemory() / (1024 * 1024)} MB")
        
        // Log the current working directory
        logger.info("Current working directory: ${System.getProperty("user.dir")}")
        
        logger.info("=== APPLICATION READY ===")
    }
}