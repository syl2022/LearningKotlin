package no.demo.learnkotlin.model

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import jakarta.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long,
    @Column(nullable = false)
    val customerId: Long,
    @Column(nullable = false)
    val supplierId: Long,
    @Column(nullable = false)
    val deliveryAddress: String,
    @Column(nullable = false)
    val productId: Long,
    @Column(nullable = false)
    val productJson: String,
    @Column(nullable = false)
    val orderStatus: OrderStatus
) {

    fun validate() {
        require(customerId > 0) { "Invalid Customer Id" }
        require(deliveryAddress.isNotBlank()) { "Delivery address cannot be blank" }
        require(productId > 0) { "Invalid Product Id" }
        require(productJson.isNotBlank()) { "Json cannot be blank" }
    }
}

@JsonDeserialize(using = OrderStatusDeserializer::class)
enum class OrderStatus {
    ORDERED,
    PREPARING,
    SENT,
    ONTHEWAY,
    DELIVERED;
    companion object {
        fun fromString(value: String): OrderStatus? {
            return values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}

class OrderStatusDeserializer : JsonDeserializer<OrderStatus>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): OrderStatus {
        val value = p.text
        return OrderStatus.fromString(value) ?: throw IllegalArgumentException("Invalid order status: $value")
    }
}