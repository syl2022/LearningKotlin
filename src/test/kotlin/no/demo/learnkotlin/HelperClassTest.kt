package no.demo.learnkotlin

import no.demo.learnkotlin.helper.Helper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class HelperClassTest {
    @Test
    fun `test randomString generates 8 character string`() {
        val randomString = Helper.randomString()
        assertNotNull(randomString)
        assertEquals(8, randomString.length)
    }

    @Test
    fun `test randomString generates string with allowed characters`() {
        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString = Helper.randomString()

        assertTrue(randomString.all { it in allowedChars })
    }

    @Test
    fun `test randomString generates different strings`() {
        val string1 = Helper.randomString()
        val string2 = Helper.randomString()

        assertNotEquals(string1, string2, "Generated strings are identical")
    }

    @Test
    fun `test encodeString with valid input`() {
        val input = "Learn Kotlin"
        val expectedOutput = Base64.getEncoder().encodeToString(input.toByteArray(Charsets.UTF_8))
        val actualOutput = Helper.encodeString(input)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `test encodeString with empty input`() {
        val input = ""
        val expectedOutput = Base64.getEncoder().encodeToString(input.toByteArray(Charsets.UTF_8))
        val actualOutput = Helper.encodeString(input)
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `test encodeString with special characters`() {
        val input = "Learn Kotlin"
        val expectedOutput = Base64.getEncoder().encodeToString(input.toByteArray(Charsets.UTF_8))
        val actualOutput = Helper.encodeString(input)
        assertEquals(expectedOutput, actualOutput)
    }
}