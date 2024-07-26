package no.demo.learnkotlin.services

import no.demo.learnkotlin.model.RegistrationRequest
import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val authService: AuthService,
    private val userRepository: UserRepository
) {
    fun register(request: RegistrationRequest): User? {
        if(authService.authenticateFirstTimeUser(request.user)) {
            val user=userRepository.registerUser(request.userDetails)
            if(user!=null) return user
        }
        return null
    }
}
