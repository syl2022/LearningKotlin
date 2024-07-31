package no.demo.learnkotlin.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableMethodSecurity
class SecurityConfiguration {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/healthcheck").permitAll()
                    .requestMatchers("/user/login").permitAll()
                    .requestMatchers("/user/register").authenticated()
                    .anyRequest().authenticated()

            }
            .oauth2ResourceServer { oauth2 -> oauth2
                .jwt(withDefaults())
            }.build()
    }

}
