package no.demo.learnkotlin.repository

import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.model.UserDetails
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun save(user: UserDetails): User? // save to database
}
