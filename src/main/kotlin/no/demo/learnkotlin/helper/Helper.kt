package no.demo.learnkotlin.helper

import java.io.UnsupportedEncodingException
import java.util.*

private const val CONFIG = "application.properties"

object PropertiesReader {
    private val properties = Properties()

    init {
        val file = this::class.java.classLoader.getResourceAsStream(CONFIG)
        properties.load(file)
    }

    fun getProperty(key: String): String = properties.getProperty(key)
}

object Helper {
    fun randomString(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val random = List(8) { charPool.random() }.joinToString("")
        return random
    }

    @Throws(UnsupportedEncodingException::class)
    fun encodeString(value: String): String {
        return try {
            val bytes = value.toByteArray(Charsets.UTF_8)
            Base64.getEncoder().encodeToString(bytes)
        } catch (e: UnsupportedEncodingException) {
            throw UnsupportedEncodingException("UTF-8 encoding is not supported")
        }
    }
}
