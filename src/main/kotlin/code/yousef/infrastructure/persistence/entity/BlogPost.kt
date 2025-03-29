package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "blog_posts")
class BlogPost : PanacheEntity() {
    lateinit var title: String
    
    @Column(length = 500)
    lateinit var summary: String
    
    @Lob
    @Column(columnDefinition = "TEXT")
    lateinit var content: String
    
    lateinit var imageUrl: String
    
    @ElementCollection
    lateinit var tags: List<String>
    
    @Column(name = "publish_date")
    lateinit var publishDate: LocalDateTime
    
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime
    
    @Column(name = "updated_at")
    lateinit var updatedAt: LocalDateTime
    
    var published: Boolean = false
    
    @Column(unique = true)
    lateinit var slug: String
}
