package no.demo.learnkotlin.helper

import java.io.UnsupportedEncodingException
import java.util.*

private const val CONFIG = "config.properties"

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
        val stringAsByteArray = value.toByteArray()
        val utf8String = String(stringAsByteArray, Charsets.UTF_8)
        return utf8String
    }
}
