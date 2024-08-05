package no.demo.learnkotlin

import com.fasterxml.jackson.databind.ObjectMapper
import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.config.CustomTestConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(CustomTestConfiguration::class)
class APITest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @Test
    fun testMyApi() {
        val chainId = "someChainId"
        val channel = "someChannel"
        val authHeader = "learnkotlin:learnkotlin:$chainId:$channel"
        val encodedAuthHeader = Base64.getEncoder().encodeToString(authHeader.toByteArray())

            val user = User(
                id = 1,
                username = "validUser",
                email = "user@example.com",
            )
            mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:$port/user/login")
                .header("Authorization", "Bearer $encodedAuthHeader")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk)

    }
}