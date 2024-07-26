package no.demo.learnkotlin.controller

import jakarta.validation.Valid
import no.demo.learnkotlin.model.RegistrationRequest
import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.services.AuthService
import no.demo.learnkotlin.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegistrationRequest): ResponseEntity<*> {
        val user = userService.register(request)
        if(user!=null) return ResponseEntity.ok("User registered")
        return ResponseEntity.status(401).body("User not registered ! Try again !")
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody user: User): ResponseEntity<String> {
        return if (authService.authenticateUser(user.username, user.password)) {
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
