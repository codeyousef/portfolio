package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import java.time.LocalDateTime
import jakarta.persistence.*
import java.util.UUID

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
    lateinit var createdAt: LocalDateTime
    
    @Column(name = "updated_at")
    lateinit var updatedAt: LocalDateTime
    
    var featured: Boolean = false
}
