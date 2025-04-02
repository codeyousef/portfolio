package code.yousef.infrastructure.template

import code.yousef.presentation.dto.response.ServiceResponse
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

@ApplicationScoped
class ServiceTemplates {
    @Location("base-layout.html")
    @Inject
    lateinit var baseTemplate: Template

    /**
     * Builds the services page using Kotlin HTML DSL
     */
    fun buildServicesPage(services: List<ServiceResponse>): TemplateInstance {
        val contentBuilder = StringBuilder()
        contentBuilder.appendHTML().div {
            // Hero section
            section(classes = "hero services-hero") {
                div(classes = "hero-content fade-in") {
                    h1 { +"Professional Services" }
                    p {
                        +"Strategic solutions tailored to your business needs. Let's bring your ideas to life with cutting-edge technology."
                    }
                    div {
                        a(href = "#services-list", classes = "cta-button") { +"View Services" }
                        a(href = "#contact", classes = "cta-button secondary") { +"Get a Quote" }
                    }
                }

                div(classes = "hero-glow") { }
                div(classes = "hero-glow-2") { }
            }
            
            // Services section
            section(classes = "section") {
                id = "services-list"
                div(classes = "section-header") {
                    h2(classes = "section-title") { +"What I Offer" }
                    p(classes = "section-desc") {
                        +"Comprehensive services to help your business grow with modern technology solutions."
                    }
                }
                
                div(classes = "services-grid") {
                    services.forEach { service ->
                        div(classes = "service-card") {
                            div(classes = "service-icon") {
                                i(classes = service.iconClass) { }
                            }
                            h3(classes = "service-title") { +service.title }
                            p(classes = "service-desc") { +service.shortDescription }

                            if (service.price != null) {
                                div(classes = "service-price") { +service.price }
                            }

                            if (service.features.isNotEmpty()) {
                                ul(classes = "service-features") {
                                    service.features.forEach { feature ->
                                        li { +feature }
                                    }
                                }
                            }

                            a(href = service.detailsLink, classes = "cta-button") { +"Details" }
                        }
                    }
                }
            }
            
            // Call to action section
            section(classes = "section cta-section") {
                div(classes = "cta-container") {
                    div(classes = "cta-content") {
                        h2 { +"Ready to start your project?" }
                        p { +"Get in touch for a free consultation and let's discuss how I can help you achieve your goals." }
                    }
                    div(classes = "cta-actions") {
                        a(href = "/contact", classes = "cta-button") { +"Contact Me" }
                    }
                }
            }
        }

        return baseTemplate
            .data("title", "Professional Services")
            .data("content", contentBuilder.toString())
    }

    /**
     * Builds a detailed service page
     */
    fun buildServiceDetailsPage(service: ServiceResponse?): TemplateInstance {
        val contentBuilder = StringBuilder()
        contentBuilder.appendHTML().div {
            if (service == null) {
                div(classes = "error-container") {
                    h1 { +"Service Not Found" }
                    p { +"The service you're looking for does not exist or has been removed." }
                    a(href = "/services", classes = "cta-button") { +"Back to Services" }
                }
            } else {
                // Service details hero
                section(classes = "hero service-details-hero") {
                    div(classes = "hero-content fade-in") {
                        h1 { +service.title }
                        p { +service.shortDescription }

                        if (service.price != null) {
                            div(classes = "service-price-tag") {
                                +"Starting at "
                                span { +service.price }
                            }
                        }

                        a(href = "#service-content-section", classes = "cta-button") { +service.ctaText }
                    }
                    
                    div(classes = "hero-glow") { }
                }
                
                // Main content section
                section(classes = "section service-content-section") {
                    div(classes = "service-content") {
                        id = "service-content-section"
                        // Service icon
                        div(classes = "service-icon-large") {
                            i(classes = service.iconClass) { }
                        }
                        
                        // Description
                        div(classes = "service-full-description") {
                            unsafe { +service.fullDescription }
                        }
                    }
                    
                    // Features section if available
                    if (service.features.isNotEmpty()) {
                        div(classes = "service-features-container") {
                            h3 { +"Key Features" }
                            ul(classes = "service-features-list") {
                                service.features.forEach { feature ->
                                    li { 
                                        i(classes = "feature-check-icon fas fa-check-circle") { }
                                        span { +feature }
                                    }
                                }
                            }
                        }
                    }
                }
                
                // Related services section could be added here
                
                // Call to action section
                section(classes = "section cta-section") {
                    div(classes = "cta-container") {
                        div(classes = "cta-content") {
                            h2 { +"Interested in this service?" }
                            p { +"Get in touch to discuss how this service can benefit your business." }
                        }
                        div(classes = "cta-actions") {
                            a(href = service.ctaLink, classes = "cta-button") { +"Contact" }
                        }
                    }
                }
            }
        }

        return baseTemplate
            .data("title", service?.title ?: "Service Not Found")
            .data("content", contentBuilder.toString())
    }
}
