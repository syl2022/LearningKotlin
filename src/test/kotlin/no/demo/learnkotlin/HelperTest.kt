package no.demo.learnkotlin

import no.demo.learnkotlin.helper.Helper
import org.junit.Assert.*
import org.junit.Test

class HelperTest {
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

        // Since the function generates random strings, there's a very small chance this test might fail.
        assertNotEquals(string1, string2, "Generated strings are identical which is highly unlikely")
    }
}