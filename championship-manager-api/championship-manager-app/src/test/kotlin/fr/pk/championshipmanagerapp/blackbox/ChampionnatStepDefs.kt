package fr.pk.championshipmanagerapp.blackbox

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import io.cucumber.java8.En
import org.assertj.core.api.Assert
import org.assertj.core.api.Assertions
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource

class ChampionnatStepDefs(private val graphqlTemplate: TestGraphQLTemplate) : En {

    @Value("classpath:graphql/new-championnat.graphql")
    private lateinit var newChampionnatQuery: Resource

    @Value("classpath:graphql/get-championnat.graphql")
    private lateinit var getChampionnatQuery: Resource

    init {
        When("l'utilisateur crée un championnat") {
            this.graphqlTemplate.post(newChampionnatQuery)
        }

        Then("l'utilisateur récupère le championnat") {
            val response = this.graphqlTemplate.post(getChampionnatQuery)
            val asMap = JSONObject(response?.data as Map<*, *>)["championnats"].toString()
            val championnats: List<ChampionnatDto> = jacksonObjectMapper().readValue(asMap)
            Assertions.assertThat(championnats.find { it.nom == "test" }).isNotNull
            Assertions.assertThat(championnats.size).isEqualTo(1)
        }
    }
}
