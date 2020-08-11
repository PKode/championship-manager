package fr.pk.championshipmanagerapp.blackbox

import fr.pk.championshipmanagerapplication.dto.EquipeDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

class EquipeStepDefs(private val graphqlTemplate: TestGraphQLTemplate,
                     private val scenarioContext: ScenarioContext) : En {

    @Value("classpath:graphql/new-equipe.graphql")
    private lateinit var newEquipeQuery: URL

    @Value("classpath:graphql/get-equipes.graphql")
    private lateinit var getEquipeQuery: URL

    @Value("classpath:graphql/delete-equipe.graphql")
    private lateinit var deleteEquipeQuery: URL

    init {
        When("l'utilisateur crée les équipes avec les informations suivantes") { data: DataTable ->
            data.asMaps().forEach {
                val result: EquipeDto = this.graphqlTemplate.post(newEquipeQuery, it).pluck("equipe")
                scenarioContext.put(ContextKey.LAST_EQUIPE_ID, result.id!!)
            }
        }
        When("l'utilisateur supprime l'équipe avec l'id {string}") { equipeId: String ->
            this.graphqlTemplate.post(deleteEquipeQuery, mapOf("id" to equipeId))
        }

        Then("l'utilisateur affiche les équipes") { data: DataTable ->
            val equipes: List<EquipeDto> = this.graphqlTemplate.post(getEquipeQuery).pluck("equipes")

            //TODO: Find better way to assert more global for all this kind of assert whatever part we assert.
            val expectedEquipeName = data.asList()

            assertThat(equipes.find { it.nom in expectedEquipeName }).isNotNull
            assertThat(equipes.size).isEqualTo(expectedEquipeName.size)
        }

        Then("l'utilisateur ne retrouve aucune des équipes suivantes dans la liste des équipes") { data: DataTable ->
            val equipes: List<Map<String, Any>> = this.graphqlTemplate.post(getEquipeQuery).pluck("equipes")

            val expectedEquipe = data.asMaps()

            expectedEquipe.forEach {
                assertThat(equipes).doesNotContain(it)
            }
        }
    }
}
