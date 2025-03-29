package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
class Project : PanacheEntity() {
    lateinit var title: String
    lateinit var description: String
    lateinit var imageUrl: String
    lateinit var modelUrl: String
    
    @ElementCollection
    lateinit var technologies: List<String>
    
    lateinit var githubUrl: String
    lateinit var demoUrl: String
    
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime
    
    @Column(name = "updated_at")
    lateinit var updatedAt: LocalDateTime
    
    var featured: Boolean = false
}
