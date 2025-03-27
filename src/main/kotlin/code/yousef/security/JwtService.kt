//package code.yousef.security
//
//import code.yousef.model.User
//import io.smallrye.jwt.build.Jwt
//import java.time.Duration
//import java.time.Instant
//import java.util.*
//import jakarta.enterprise.context.ApplicationScoped
//import org.eclipse.microprofile.jwt.JsonWebToken
//import org.eclipse.microprofile.config.inject.ConfigProperty
//import jakarta.inject.Inject
//
//@ApplicationScoped
//class JwtService {
//
//    @ConfigProperty(name = "mp.jwt.verify.issuer")
//    lateinit var issuer: String
//
//    @ConfigProperty(name = "quarkus.security.jwt.token.lifespan")
//    var lifespan: Long = 86400 // 24 hours default
//
//    fun generateToken(user: User): String {
//        return Jwt.issuer(issuer)
//            .subject(user.username)
//            .upn(user.email)
//            .groups(setOf(user.role.toString()))
//            .expiresAt(Instant.now().plus(Duration.ofSeconds(lifespan)))
//            .sign()
//    }
//
//    fun validateToken(token: String): String? {
//        return try {
//            // For simplicity, we're just decoding the token here
//            // In a real implementation, you would use MP-JWT to validate the token
//            val parts = token.split(".")
//            if (parts.size != 3) return null
//
//            val payload = String(Base64.getDecoder().decode(parts[1]))
//            // Extract subject from payload (simplified)
//            val subjectMatch = "\"sub\":\"([^\"]+)\"".toRegex().find(payload)
//            subjectMatch?.groupValues?.get(1)
//        } catch (e: Exception) {
//            null
//        }
//    }
//}
