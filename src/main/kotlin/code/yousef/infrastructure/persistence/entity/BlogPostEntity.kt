package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "blog_posts")
class BlogPostEntity : PanacheEntityBase() {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

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