package no.demo.learnkotlin

import no.demo.learnkotlin.model.NationalIdentificationType
import no.demo.learnkotlin.model.UserDetails
import no.demo.learnkotlin.model.UserType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserControllerTest {

    @Test
    fun `should create UserDetails with valid data`() {
        val userDetails = UserDetails(
            id = 1,
            username = "validUser",
            userType = UserType.CUSTOMER,
            email = "user@example.com",
            address = "test address",
            telephone = "+1234567890",
            idType = NationalIdentificationType.PASSPORT
        )
        userDetails.validate()
    }

    @Test
    fun `should throw exception for blank username`() {
        val exception = assertThrows<IllegalArgumentException> {
            val userDetails = UserDetails(
                id = 1,
                username = "",
                userType = UserType.CUSTOMER,
                email = "user@example.com",
                address = "test address",
                telephone = "+1234567890",
                idType = NationalIdentificationType.PASSPORT
            )
            userDetails.validate()
        }
        assert(exception.message == "Invalid Username")
    }

    @Test
    fun `should throw exception for invalid email`() {
        val exception = assertThrows<IllegalArgumentException> {
            val userDetails = UserDetails(
                id = 1,
                username = "validUser",
                userType = UserType.CUSTOMER,
                email = "invalid email",
                address = "test address",
                telephone = "+1234567890",
                idType = NationalIdentificationType.PASSPORT
            )
            userDetails.validate()
        }
        assert(exception.message == "Invalid email address")
    }

    @Test
    fun `should throw exception for blank address`() {
        val exception = assertThrows<IllegalArgumentException> {
            val userDetails = UserDetails(
                id = 1,
                username = "validUser",
                userType = UserType.CUSTOMER,
                email = "user@example.com",
                address = "",
                telephone = "+1234567890",
                idType = NationalIdentificationType.PASSPORT
            )
            userDetails.validate()
        }
        assert(exception.message == "Address cannot be blank")
    }

    @Test
    fun `should throw exception for invalid telephone number`() {
        val exception = assertThrows<IllegalArgumentException> {
            val userDetails = UserDetails(
                id = 1,
                username = "validUser",
                userType = UserType.CUSTOMER,
                email = "user@example.com",
                address = "123 Main St",
                telephone = "invalid-phone",
                idType = NationalIdentificationType.PASSPORT
            )
            userDetails.validate()
        }
        assert(exception.message == "Invalid phone number")
    }
}
