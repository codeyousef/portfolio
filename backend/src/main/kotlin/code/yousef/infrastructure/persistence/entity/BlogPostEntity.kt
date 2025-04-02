package code.yousef.infrastructure.persistence.entity

import code.yousef.infrastructure.persistence.converter.InstantAttributeConverter
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import kotlinx.datetime.Instant
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Entity
@Table(name = "blog_posts")
class BlogPostEntity : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    lateinit var title: String

    @Column(length = 500)
    lateinit var summary: String

    @Column(columnDefinition = "TEXT")
    lateinit var content: String

    lateinit var imageUrl: String

    @ElementCollection(fetch = FetchType.EAGER)
    lateinit var tags: List<String>

    @Column(name = "publish_date")
    @Convert(converter = InstantAttributeConverter::class)
    lateinit var publishDate: Instant

    @Column(name = "created_at")
    @Convert(converter = InstantAttributeConverter::class)
    lateinit var createdAt: Instant

    @Column(name = "updated_at")
    @Convert(converter = InstantAttributeConverter::class)
    lateinit var updatedAt: Instant

    var published: Boolean = false

    @Column(unique = true)
    lateinit var slug: String
}