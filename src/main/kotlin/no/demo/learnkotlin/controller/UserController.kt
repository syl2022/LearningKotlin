package no.demo.learnkotlin.controller

import jakarta.validation.Valid
import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.model.UserDetails
import no.demo.learnkotlin.services.AuthService
import no.demo.learnkotlin.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody user: UserDetails): ResponseEntity<String> {
        try {
            user.validate()
            val otp = userService.register(user)
            if (otp != null) return ResponseEntity.ok("User registered. Verify the OTP: $otp")
            return ResponseEntity.status(401).body("User not registered ! Try again !")
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(e.message)
        }
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody user: User): ResponseEntity<String> {
        try {
            return if (userService.authenticateUser(user.username, user.password)) {
                ResponseEntity.ok("Login successful")
            } else {
                ResponseEntity.status(401).body("Invalid credentials")
            }
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(e.message)
        }
    }
}
