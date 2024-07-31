package no.demo.learnkotlin.controller

import jakarta.validation.Valid
import no.demo.learnkotlin.model.Order
import no.demo.learnkotlin.model.OrderStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController {

    @PostMapping("/place")
    fun register(@Valid @RequestBody order: Order): ResponseEntity<String> {
        try {
            order.validate()
            return ResponseEntity.ok(OrderStatus.PREPARING.toString())
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(e.message)
        }

    }
}