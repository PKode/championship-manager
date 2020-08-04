package fr.pk.championshipmanagerapp.blackbox

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import java.net.URL

class ChampionnatStepDefs(private val graphqlTemplate: TestGraphQLTemplate) : En {

    @Value("classpath:graphql/new-championnat.graphql")
    private lateinit var newChampionnatQuery: URL

    @Value("classpath:graphql/get-championnat.graphql")
    private lateinit var getChampionnatQuery: URL

    init {
        When("l'utilisateur crée les championnats avec les informations suivantes") { data: DataTable ->
            data.asMaps().forEach {
                this.graphqlTemplate.post(newChampionnatQuery, it)
            }
        }

        Then("l'utilisateur récupère le championnat {string}") { nom: String ->
            val response = this.graphqlTemplate.post(getChampionnatQuery)
            val asMap = JSONObject(response?.data as Map<*, *>)["championnats"].toString()
            val championnats: List<ChampionnatDto> = jacksonObjectMapper().readValue(asMap)

            assertThat(championnats.find { it.nom == nom }).isNotNull
            assertThat(championnats.size).isEqualTo(1)
        }
    }
}
