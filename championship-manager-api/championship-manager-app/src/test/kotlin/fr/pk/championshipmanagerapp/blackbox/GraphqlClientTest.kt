package fr.pk.championshipmanagerapp.blackbox

import com.expediagroup.graphql.client.GraphQLClient
import com.expediagroup.graphql.types.GraphQLResponse
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.util.PropertyPlaceholderHelper
import java.net.URL

@KtorExperimentalAPI
@Component
@Profile("blackbox")
class GraphqlClientTest(private val graphQLClient: GraphQLClient<*>,
                        private val scenarioContext: ScenarioContext) {

    fun execute(query: URL, operationName: String, variables: Map<String, Any>? = null): GraphQLResponse<*> =
            runBlocking {
                graphQLClient.execute(query.readText(), operationName, variables, Map::class.java)
            }
}

// TODO: see if and how we want to go more deeper like pluck in rxjs
inline fun <reified T> GraphQLResponse<*>.pluck(property: String): T {
    return jacksonObjectMapper.readValue(JSONObject(this.data as Map<*, *>)[property].toString())
}
