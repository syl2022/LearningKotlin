package no.demo.learnkotlin.repository

import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.model.UserDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findUserInAuth0Directory(username: String, password: String): User? //check with auth0
    fun registerUser(userDetails: UserDetails): User? // save to database
}
