package fr.pk.championshipmanagerapp.blackbox

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

fun String.content() = {}::class.java.getResource(this)?.readText() ?: error("Payload not found")

/**
 * Extract expected data from String.
 * Try to parse it as json or read in file if not parsable
 */
inline fun <reified T> String.extractExpected(transform: String.() -> String): T {
    return try {
        jacksonObjectMapper().readValue(transform(this))
    } catch (parseException: JsonParseException) {
        jacksonObjectMapper().readValue(transform(this.content()))
    }
}
