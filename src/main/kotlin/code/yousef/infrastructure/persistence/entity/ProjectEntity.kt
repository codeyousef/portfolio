package code.yousef.infrastructure.persistence.entity

import code.yousef.infrastructure.persistence.converter.InstantAttributeConverter
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.time.Instant
import java.util.*
import kotlin.uuid.ExperimentalUuidApi

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
