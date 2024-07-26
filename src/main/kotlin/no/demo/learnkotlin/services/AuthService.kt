package no.demo.learnkotlin.services

import no.demo.learnkotlin.model.User
import org.springframework.stereotype.Service

@Service
class AuthService {
    fun authenticateFirstTimeUser(user: User): Boolean {
        val user = findUserInAuth0Directory(user.username, user.password)

        // TODO: more steps of validating the user details(address/ID etc) will be added later

        return user != null
    }

    fun authenticateUser(username: String, password: String): Boolean {
        val user = findUserInAuth0Directory(username, password)
        return user != null
    }

    fun findUserInAuth0Directory(username: String, password: String): User? {
        return User()
    }//check with auth0
}
