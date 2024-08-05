package no.demo.learnkotlin.config

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import java.time.Instant
import java.util.*

class TestJwtDecoder : JwtDecoder {
    override fun decode(token: String?): Jwt {
        val decoded = String(Base64.getDecoder().decode(token))
        val parts = decoded.split(":")

        if (parts.size < 4) {
            throw JwtException("Invalid token format")
        }

        val headers = mapOf("alg" to "none")
        val claims = mapOf(
            "user_name" to parts[0],
            "scope" to listOf(parts[1]),
            "chain_id" to parts[2],
            "sales_channel" to parts[3]
        )

        return Jwt.withTokenValue(token!!)
            .headers { it.putAll(headers) }
            .claims { it.putAll(claims) }
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build()
    }
}
