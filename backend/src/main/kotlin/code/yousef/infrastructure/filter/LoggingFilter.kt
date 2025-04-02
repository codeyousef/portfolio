package code.yousef.infrastructure.filter

import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.ext.Provider
import org.jboss.logging.Logger

@Provider
class LoggingFilter : ContainerRequestFilter, ContainerResponseFilter {
    private val logger = Logger.getLogger(LoggingFilter::class.java)

    override fun filter(requestContext: ContainerRequestContext) {
        logger.info("Request: ${requestContext.method} ${requestContext.uriInfo.path}")
        logger.info("Headers: ${requestContext.headers}")
    }

    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext) {
        logger.info("Response for ${requestContext.method} ${requestContext.uriInfo.path}: ${responseContext.status}")
        logger.info("Content-Type: ${responseContext.mediaType}")
    }
}
