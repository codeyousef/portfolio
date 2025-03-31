package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "services")
class ServiceEntity : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    lateinit var title: String
    
    @Column(length = 500)
    lateinit var shortDescription: String
    
    @Column(columnDefinition = "TEXT")
    lateinit var fullDescription: String
    
    lateinit var iconClass: String
    
    var price: String? = null
    
    @ElementCollection(fetch = FetchType.EAGER)
    var features: List<String> = listOf()
    
    var ctaText: String = "Hire Me"
    
    var ctaLink: String = "/contact"
    
    var displayOrder: Int = 0
    
    var featured: Boolean = false
    
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime
    
    @Column(name = "updated_at")
    lateinit var updatedAt: LocalDateTime
}
