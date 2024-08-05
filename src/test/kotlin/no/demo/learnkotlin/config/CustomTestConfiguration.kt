package no.demo.learnkotlin.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.jwt.JwtDecoder

@TestConfiguration
class CustomTestConfiguration {

    @Bean
    @Primary
    fun partnerTestJwtDecoder(): JwtDecoder = TestJwtDecoder()
}
