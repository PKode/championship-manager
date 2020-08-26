package fr.pk.championshipmanagerapp.blackbox

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Profile

// TODO: Remove if not usefull
@TestConfiguration
@Profile("blackbox")
class BlackBoxConfiguration {

}

val jacksonObjectMapper = jacksonObjectMapper().configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false)
