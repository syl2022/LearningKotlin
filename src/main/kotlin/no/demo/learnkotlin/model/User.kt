package no.demo.learnkotlin.model

import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true, nullable = false)
    val username: String="",
    @Column(nullable = false)
    val password: String="",
    @Column(unique = true,nullable = false)
    val email: String="",
)

