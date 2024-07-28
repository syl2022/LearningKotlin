package no.demo.learnkotlin.controller

import jakarta.validation.Valid
import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.model.UserDetails
import no.demo.learnkotlin.services.AuthService
import no.demo.learnkotlin.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody user: UserDetails): ResponseEntity<*> {
        val otp = userService.register(user)
        if (otp != null) return ResponseEntity.ok("User registered. Verify the OTP: $otp")
        return ResponseEntity.status(401).body("User not registered ! Try again !")
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody user: User): ResponseEntity<String> {
        return if (userService.authenticateUser(user.username, user.password)) {
            ResponseEntity.ok("Login successful")
        } else {
            ResponseEntity.status(401).body("Invalid credentials")
        }
    }

    @GetMapping("/healthcheck")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("Service is up!")
    }
}
