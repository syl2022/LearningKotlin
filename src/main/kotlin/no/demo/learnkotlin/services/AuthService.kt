package no.demo.learnkotlin.services

import no.demo.learnkotlin.helper.Helper
import no.demo.learnkotlin.model.User
import no.demo.learnkotlin.model.UserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AuthService {

    @Autowired
    private lateinit var emailService: EmailService

    fun authenticateFirstTimeUser(user: UserDetails): String? {
        val otpToVerify = verifyEmail(user)

        // TODO: more steps of validating the user details(address/ID etc) will be added later

        return otpToVerify
    }

    fun verifyEmail(user: UserDetails): String? {
        val otp: String = Helper.randomString()
        val encodedOTP: String = Helper.encodeString(otp)

        val success = emailService.sendOTPmail(user.email, user.username, encodedOTP)
        if (success)
            return encodedOTP
        else return null
    }

    fun findUserInAuth0Directory(username: String, password: String): User? {
        return User()
    }//check with auth0
}
