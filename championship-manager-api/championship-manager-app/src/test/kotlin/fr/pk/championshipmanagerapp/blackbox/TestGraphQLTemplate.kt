package fr.pk.championshipmanagerapp.blackbox

import com.expediagroup.graphql.spring.model.GraphQLRequest
import com.expediagroup.graphql.spring.model.GraphQLResponse
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
class TestGraphQLTemplate(private val restTemplate: TestRestTemplate) {

    fun post(query: URL, variables: Map<String, String> = mapOf()): GraphQLResponse? {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }

        val payload = variables.entries.fold(
                query.readText(),
                { p, map -> p.replace("\$${map.key}", map.value) }
        )

        val request = HttpEntity(GraphQLRequest(payload), headers)

        return restTemplate.exchange(
                "http://localhost:8080/graphql",
                HttpMethod.POST,
                request,
                GraphQLResponse::class.java).body
    }
}
