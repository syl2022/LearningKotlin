package no.demo.learnkotlin.services

import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {
    fun authenticateFirstTimeUser(user: User): Boolean {
        val user = userRepository.findUserInAuth0Directory(user.username, user.password)

        // TODO: more steps of validating the user details(address/ID etc) will be added later

        return user != null
    }

    fun authenticateUser(username: String, password: String): Boolean {
        val user = userRepository.findUserInAuth0Directory(username, password)
        return user != null
    }
}
