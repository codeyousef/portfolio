package code.yousef.infrastructure.persistence.entity

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "users")
class UserEntity : PanacheEntity() {
    
    @Column(unique = true)
    lateinit var username: String
    
    lateinit var password: String // Hashed password
    lateinit var name: String
    lateinit var email: String
    
    @Enumerated(EnumType.STRING)
    lateinit var role: UserRole
    
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime
    
    @Column(name = "updated_at")
    lateinit var updatedAt: LocalDateTime
    
    @Column(name = "last_login")
    var lastLogin: LocalDateTime? = null
}
