package no.demo.learnkotlin.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GenericController {
    @GetMapping("/healthcheck")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("Service is up!")
    }
}