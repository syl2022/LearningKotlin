package no.demo.learnkotlin.services

import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import no.demo.learnkotlin.helper.PropertiesReader
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmailService(private var session: Session?, private var transport: Transport?) {

    val organisationEmail = PropertiesReader.getProperty("mail.host")
    val password = PropertiesReader.getProperty("mail.secrets")
    val username = PropertiesReader.getProperty("mail.username")

    fun sendOTPmail(email: String, name: String, otp: String): Boolean {

        val subject = "Learn Kotlin verification"
        val body = """
            Hello ${name}
            For security reasons, you're required to use the following One Time Password to login:
            ${otp}
            Note: this OTP is set to expire in 5 minutes.
            
            Thanks
            Learn Kotlin Org
        """.trimIndent()

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(organisationEmail))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(email))
                setSubject(subject)
                setText(body)
            }

            val props = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.host", "smtp.gmail.com")
                put("mail.smtp.port", "587")
                put("mail.smtp.ssl.trust", "smtp.gmail.com")
            }
            if (session == null) {
                session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(organisationEmail, password)
                    }
                })
            }
            if (transport == null) {
                transport = session?.getTransport("smtps")
                transport?.connect("smtp.gmail.com", username, password)
            }
            transport?.sendMessage(message, message.allRecipients)
            println("Email sent successfully.")
            return true
        } catch (e: AuthenticationFailedException) {
            println("Authentication failed: ${e.message}")
            return false
        } catch (e: MessagingException) {
            println("Messaging exception: ${e.message}")
            return false
        } catch (e: Exception) {
            println("Unexpected exception: ${e.printStackTrace()}")
            return false
        }
    }
}