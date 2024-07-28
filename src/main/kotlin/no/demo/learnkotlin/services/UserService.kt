package no.demo.learnkotlin.services

import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.model.UserDetails
import no.demo.learnkotlin.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val authService: AuthService,
    private val userRepository: UserRepository
) {
    fun authenticateUser(username: String, password: String): Boolean {
        val user = authService.findUserInAuth0Directory(username, password)
        return user != null
    }

    fun register(user: UserDetails): String? {
        val otp = authService.authenticateFirstTimeUser(user)
        if (otp != null) {
            val user = User()//userRepository.save(user)
            if (user != null) return otp
        }
        return null
    }
}
