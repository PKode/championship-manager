package fr.pk.championshipmanagerapp.blackbox

import com.expediagroup.graphql.spring.model.GraphQLResponse
import com.fasterxml.jackson.core.io.JsonStringEncoder
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

@Component
@Profile("blackbox")
class TestGraphQLTemplate(private val restTemplate: TestRestTemplate,
                          private val queryWrapper: String) {

    val query = """
    query championnats {
        championnats {
            id
            nom
            saisons {
                annee
            }
        }
    }
""".trimIndent()

    fun post(query: Resource): GraphQLResponse? {

        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val escapedQuery = JsonStringEncoder.getInstance()
                .quoteAsString(StreamUtils.copyToString(query.inputStream, StandardCharsets.UTF_8))
                .joinToString("")
        val payload = queryWrapper.replace("__payload__", escapedQuery)
        val request = HttpEntity(payload, headers)

        return restTemplate.exchange(
                "http://localhost:8080/graphql",
                HttpMethod.POST,
                request,
                GraphQLResponse::class.java).body
    }
}
