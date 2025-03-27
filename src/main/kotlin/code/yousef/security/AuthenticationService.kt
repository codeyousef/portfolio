//package code.yousef.security
//
//import code.yousef.model.User
//import code.yousef.service.UserService
//import io.quarkus.security.AuthenticationFailedException
//import io.smallrye.mutiny.Uni
//import java.util.*
//import jakarta.enterprise.context.ApplicationScoped
//import jakarta.inject.Inject
//
//@ApplicationScoped
//class AuthenticationService {
//
//    @Inject
//    lateinit var userService: UserService
//
//    @Inject
//    lateinit var jwtService: JwtService
//
//    fun login(username: String, password: String): Uni<LoginResponse> {
//        return userService.authenticate(username, password)
//            .onItem().transform { user ->
//                if (user != null) {
//                    val token = jwtService.generateToken(user)
//                    LoginResponse(token, user)
//                } else {
//                    throw AuthenticationFailedException("Invalid credentials")
//                }
//            }
//    }
//
//    fun validateToken(token: String): Uni<User> {
//        val username = jwtService.validateToken(token)
//
//        return if (username != null) {
//            userService.getUserByUsername(username)
//        } else {
//            Uni.createFrom().failure(AuthenticationFailedException("Invalid token"))
//        }
//    }
//}
