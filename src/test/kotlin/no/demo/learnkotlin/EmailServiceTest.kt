package no.demo.learnkotlin

import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import no.demo.learnkotlin.services.EmailService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@WebMvcTest(EmailService::class)
class EmailServiceTest {

    private lateinit var session: Session
    private lateinit var transport: Transport

    @MockBean
    private lateinit var emailService: EmailService

    @Value("\${host}")
    val organisationEmail = ""
    val password = "dummyPass"
    val customerEmail = "customer@gmail.com"

    @BeforeEach
    fun setUp() {

        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
            put("mail.smtp.ssl.trust", "smtp.gmail.com")
        }
        session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(organisationEmail, password)
            }
        })
        transport = mock(Transport::class.java)
        emailService = EmailService(session, transport)
    }

    @Test
    fun `test email is created with correct details`() {
        val otp = "123456"

        emailService.sendOTPmail(customerEmail, "kotlinCustomer", otp)

        val messageCaptor = ArgumentCaptor.forClass(MimeMessage::class.java)
        verify(transport).sendMessage(messageCaptor.capture(), any())

        val message = messageCaptor.value
        assertEquals(organisationEmail, (message.from[0] as InternetAddress).address)
        assertEquals(customerEmail, (message.allRecipients[0] as InternetAddress).address)
        assertEquals("Learn Kotlin verification", message.subject)
        assertTrue((message.content as String).contains(otp))
    }

    @Test
    fun `test email is sent successfully`() {
        val otp = "123456"

        emailService.sendOTPmail(customerEmail, "kotlinCustomer", otp)

        verify(transport, times(1)).sendMessage(any(MimeMessage::class.java), any())
    }

    @Test
    fun `test email send handles messaging exception`() {
        val otp = "123456"

        doThrow(MessagingException::class.java).`when`(transport).sendMessage(any(MimeMessage::class.java), any())

        assertDoesNotThrow { emailService.sendOTPmail(customerEmail, "kotlinCustomer", otp) }
    }

}



