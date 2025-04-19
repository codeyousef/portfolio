package code.yousef.infrastructure.summon

import code.yousef.summon.integrations.quarkus.EnhancedQuarkusExtension
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import jakarta.inject.Singleton

/**
 * Producer class for Summon Quarkus integration components.
 * This class provides CDI beans for Summon components that need to be injected.
 */
@ApplicationScoped
class QuarkusExtensionProducer {

    /**
     * Produces an EnhancedSummonRenderer instance for injection.
     * This makes the EnhancedQuarkusExtension.EnhancedSummonRenderer available for CDI injection.
     * 
     * @return A singleton instance of EnhancedQuarkusExtension.EnhancedSummonRenderer
     */
    @Produces
    @Singleton
    fun produceSummonRenderer(): EnhancedQuarkusExtension.EnhancedSummonRenderer {
        println("QuarkusExtensionProducer.produceSummonRenderer() called - creating EnhancedSummonRenderer instance")
        println("This is a critical log message to verify if the producer method is being called")
        System.err.println("CRITICAL: QuarkusExtensionProducer.produceSummonRenderer() called")

        try {
            println("About to create EnhancedSummonRenderer instance")
            System.err.println("CRITICAL: About to create EnhancedSummonRenderer instance")

            val renderer = EnhancedQuarkusExtension.EnhancedSummonRenderer()

            println("EnhancedSummonRenderer instance created successfully: ${renderer.javaClass.name}")
            System.err.println("CRITICAL: EnhancedSummonRenderer instance created successfully: ${renderer.javaClass.name}")

            // Test the renderer to make sure it's working
            println("Testing EnhancedSummonRenderer with a simple component")
            System.err.println("CRITICAL: Testing EnhancedSummonRenderer with a simple component")

            try {
                val testHtml = renderer.renderToString {
                    // Simple test component
                }
                println("Test rendering successful, result length: ${testHtml.length}")
                System.err.println("CRITICAL: Test rendering successful, result length: ${testHtml.length}")
                println("First 100 chars of test HTML: ${testHtml.take(100)}")
            } catch (e: Exception) {
                println("ERROR testing EnhancedSummonRenderer: ${e.message}")
                System.err.println("CRITICAL ERROR testing EnhancedSummonRenderer: ${e.message}")
                println("Exception type: ${e.javaClass.name}")
                println("Stack trace: ${e.stackTraceToString()}")
                System.err.println("CRITICAL: Exception type: ${e.javaClass.name}")
                System.err.println("CRITICAL: Stack trace: ${e.stackTraceToString()}")
                // Continue despite the error - we still want to return the renderer
            }

            return renderer
        } catch (e: Exception) {
            println("ERROR creating EnhancedSummonRenderer: ${e.message}")
            System.err.println("CRITICAL ERROR creating EnhancedSummonRenderer: ${e.message}")
            println("Exception type: ${e.javaClass.name}")
            println("Stack trace: ${e.stackTraceToString()}")
            System.err.println("CRITICAL: Exception type: ${e.javaClass.name}")
            System.err.println("CRITICAL: Stack trace: ${e.stackTraceToString()}")
            throw e  // Re-throw the exception to fail fast
        }
    }
}
