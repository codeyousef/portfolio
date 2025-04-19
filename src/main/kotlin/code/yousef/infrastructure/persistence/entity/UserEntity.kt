package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntityBase
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class UserEntity : PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(unique = true)
    lateinit var username: String

    @Column(nullable = false)
    var password: String = ""

    lateinit var name: String
    lateinit var email: String

    @Enumerated(EnumType.STRING)
    lateinit var role: UserRole

    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "last_login")
    var lastLogin: LocalDateTime? = null
}
