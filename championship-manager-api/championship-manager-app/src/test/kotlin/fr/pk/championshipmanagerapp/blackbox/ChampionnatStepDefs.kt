package fr.pk.championshipmanagerapp.blackbox

import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
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

        Then("l'utilisateur affiche les championnats") { data: DataTable ->
            val championnats: List<ChampionnatDto> = this.graphqlTemplate.post(getChampionnatQuery).pluck("championnats")

            val expectedChampionnatName = data.asList()
            assertThat(championnats.find { it.nom in expectedChampionnatName }).isNotNull
            assertThat(championnats.size).isEqualTo(expectedChampionnatName.size)
        }
    }
}
