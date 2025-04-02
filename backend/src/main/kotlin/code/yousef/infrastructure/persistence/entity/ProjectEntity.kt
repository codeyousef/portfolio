package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock
import jakarta.persistence.*
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import code.yousef.infrastructure.persistence.converter.InstantAttributeConverter

@OptIn(ExperimentalUuidApi::class)
@Entity
class ProjectEntity : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    lateinit var title: String
    lateinit var description: String
    lateinit var imageUrl: String
    lateinit var modelUrl: String

    @ElementCollection(fetch = FetchType.EAGER)
    lateinit var technologies: List<String>
    
    lateinit var githubUrl: String
    lateinit var demoUrl: String
    
    @Column(name = "created_at")
    @Convert(converter = InstantAttributeConverter::class)
    lateinit var createdAt: Instant
    
    @Column(name = "updated_at")
    @Convert(converter = InstantAttributeConverter::class)
    lateinit var updatedAt: Instant
    
    var featured: Boolean = false
}
