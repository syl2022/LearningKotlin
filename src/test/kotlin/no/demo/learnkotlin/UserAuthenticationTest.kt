package no.demo.learnkotlin

import no.demo.learnkotlin.controller.AuthController
import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.services.AuthService
import no.demo.learnkotlin.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(AuthController::class)
class UserAuthenticationTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var authService: AuthService

    @MockBean
    private lateinit var userService: UserService

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User(id = 1, username = "testuser", password = "password")
    }

    @Test
    fun testLoginSuccess() {
        Mockito.`when`(authService.authenticateUser(user.username, user.password)).thenReturn(true)

        val requestBody = """
            {
                "username": "testuser",
                "password": "password"
            }
        """

        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Login successful"))
    }

    @Test
    fun testLoginFailure() {
        Mockito.`when`(authService.authenticateUser(user.username, user.password)).thenReturn(false)

        val requestBody = """
            {
                "username": "testuser",
                "password": "wrongpassword"
            }
        """

        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isUnauthorized)
    }
}
