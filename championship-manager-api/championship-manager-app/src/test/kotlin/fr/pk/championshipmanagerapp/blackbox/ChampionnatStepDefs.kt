package fr.pk.championshipmanagerapp.blackbox

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import java.net.URL

class ChampionnatStepDefs(private val graphqlTemplate: TestGraphQLTemplate) : En {

    @Value("classpath:graphql/new-championnat.graphql")
    private lateinit var newChampionnatQuery: URL

    @Value("classpath:graphql/get-championnat.graphql")
    private lateinit var getChampionnatQuery: URL

    init {
        When("l'utilisateur crée le championnat {string}") { nom: String ->
            this.graphqlTemplate.post(newChampionnatQuery, mapOf("nom" to nom))
        }

        Then("l'utilisateur récupère le championnat {string}") { nom: String ->
            val response = this.graphqlTemplate.post(getChampionnatQuery)
            val asMap = JSONObject(response?.data as Map<*, *>)["championnats"].toString()
            val championnats: List<ChampionnatDto> = jacksonObjectMapper().readValue(asMap)
            Assertions.assertThat(championnats.find { it.nom == nom }).isNotNull
            Assertions.assertThat(championnats.size).isEqualTo(1)
        }
    }
}
