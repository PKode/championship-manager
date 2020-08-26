package fr.pk.championshipmanagerapp.blackbox

import com.expediagroup.graphql.spring.model.GraphQLRequest
import com.expediagroup.graphql.spring.model.GraphQLResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.json.JSONObject
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
                          private val scenarioContext: ScenarioContext) {

    fun post(query: URL, variables: Map<String, Any> = mapOf()): GraphQLResponse {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }

        // TODO: maybe see template engine like thymeleaf or velocity ?
        val payload = scenarioContext.replacePlaceHolders(variables.toMutableMap()).entries.fold(
                query.readText(),
                { q, map -> q.replace("\$${map.key}", map.value.serialize()) }
        ).replace("\\$[a-zA-Z]+".toRegex(), "null")


        val request = HttpEntity(GraphQLRequest(payload), headers)

        return restTemplate.exchange(
                "http://localhost:8080/graphql",
                HttpMethod.POST,
                request,
                GraphQLResponse::class.java).body ?: error("No response for request:: $request")
    }
}

fun Any.serialize(): String {
    return if (this is String) this.toString() else jacksonObjectMapper.writeValueAsString(this)
}

// TODO: see if and how we want to go more deeper like pluck in rxjs
inline fun <reified T> GraphQLResponse.pluck(property: String): T {
    return jacksonObjectMapper().readValue(JSONObject(this.data as Map<*, *>)[property].toString())
}
