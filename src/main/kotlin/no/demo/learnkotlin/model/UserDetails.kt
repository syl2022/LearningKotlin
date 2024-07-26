package no.demo.learnkotlin.model

import jakarta.persistence.*


@Entity
@Table(name = "user-details")
data class UserDetails(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true, nullable = false)
    val username: String="",
    @Column(nullable = false)
    val userType: String="",
    @Column(unique = true, nullable = false)
    val address: String="",
    @Column(unique = true, nullable = false)
    val telephone: String="",
    @Column(nullable = false)
    val idType: String=""
){}
