package code.yousef.infrastructure.persistence.entity

import code.yousef.infrastructure.persistence.converter.KotlinUuidConverter
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import kotlinx.datetime.Instant
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
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

    var detailsLink: String = ""
    
    @Column(name = "created_at")
    @Convert(converter = code.yousef.infrastructure.persistence.converter.InstantAttributeConverter::class)
    lateinit var createdAt: Instant
    
    @Column(name = "updated_at")
    @Convert(converter = code.yousef.infrastructure.persistence.converter.InstantAttributeConverter::class)
    lateinit var updatedAt: Instant
}
