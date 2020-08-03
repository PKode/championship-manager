package fr.pk.championshipmanagerapp.blackbox

import com.expediagroup.graphql.spring.model.GraphQLRequest
import com.expediagroup.graphql.spring.model.GraphQLResponse
import com.fasterxml.jackson.core.io.JsonStringEncoder
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.net.URL

@Component
@Profile("blackbox")
class TestGraphQLTemplate(private val restTemplate: TestRestTemplate,
                          private val queryWrapper: String) {

    fun post(query: URL, variables: Map<String, String> = mapOf()): GraphQLResponse? {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val escapedQuery = JsonStringEncoder()
                .quoteAsString(query.readText())
                .joinToString("")

        val payload = variables.entries.fold(
                queryWrapper.replace("__payload__", escapedQuery),
                { p, map -> p.replace("\$${map.key}", map.value) }
        )
        //TODO: see to use GraphQLRequest
        val request = HttpEntity(payload, headers)
        return restTemplate.exchange(
                "http://localhost:8080/graphql",
                HttpMethod.POST,
                request,
                GraphQLResponse::class.java).body
    }
}
