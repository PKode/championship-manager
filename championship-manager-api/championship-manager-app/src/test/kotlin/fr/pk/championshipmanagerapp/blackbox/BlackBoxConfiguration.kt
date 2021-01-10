package fr.pk.championshipmanagerapp.blackbox

import com.expediagroup.graphql.client.GraphQLClient
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.util.*
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import java.net.URL

// TODO: Remove if not usefull
@TestConfiguration
@Profile("blackbox")
class BlackBoxConfiguration {

    @KtorExperimentalAPI
    @Bean
    fun graphQLClient(): GraphQLClient<*> {
        return GraphQLClient(url = URL("http://localhost:8080/graphql"))
    }
}

val jacksonObjectMapper = jacksonObjectMapper()
