package no.demo.learnkotlin.model

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.persistence.*

@Entity
@Table(name = "user-details")
data class UserDetails(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(unique = true, nullable = false)
    val username: String,
    @Column(nullable = false)
    val userType: UserType,
    @Column(unique = true,nullable = false)
    val email: String,
    @Column(unique = true, nullable = false)
    val address: String,
    @Column(unique = true, nullable = false)
    val telephone: String,
    @Column(nullable = false)
    val idType: NationalIdentificationType
){
    fun validate() {
        require(username.isNotBlank()) { "Invalid Username" }
        require("^[A-Za-z0-9+_.-]+@(.+)$".toRegex().matches(email)) { "Invalid email address" }
        require(address.isNotBlank()) { "Address cannot be blank" }
        require("^\\+?[0-9. ()-]{7,15}\$".toRegex().matches(telephone)) { "Invalid phone number" }
    }
}

@JsonDeserialize(using = UserTypeDeserializer::class)
enum class UserType{
    ADMIN,
    CUSTOMER,
    SUPPLIER;
    companion object {
        fun fromString(value: String): UserType? {
            return UserType.values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}

class UserTypeDeserializer : JsonDeserializer<UserType>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): UserType {
        val value = p.text
        return UserType.fromString(value) ?: throw IllegalArgumentException("Invalid User Type: $value")
    }
}

@JsonDeserialize(using = NationalIdTypeDeserializer::class)
enum class NationalIdentificationType{
    DRIVING_LICENCE,
    PASSPORT,
    RESIDENT_CARD;
    companion object {
        fun fromString(value: String): NationalIdentificationType? {
            return NationalIdentificationType.values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}

class NationalIdTypeDeserializer : JsonDeserializer<NationalIdentificationType>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): NationalIdentificationType {
        val value = p.text
        return NationalIdentificationType.fromString(value) ?: throw IllegalArgumentException("Invalid National Identification Type: $value")
    }
}


