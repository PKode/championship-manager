package fr.pk.championshipmanagerapp.blackbox

import fr.pk.championshipmanagerapp.blackbox.ContextKey.LAST_CHAMPIONNAT_ID
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

class ChampionnatStepDefs(private val graphqlTemplate: TestGraphQLTemplate,
                          private val scenarioContext: ScenarioContext) : En {

    @Value("classpath:graphql/new-championnat.graphql")
    private lateinit var newChampionnatMutation: URL

    @Value("classpath:graphql/get-championnat.graphql")
    private lateinit var getChampionnatQuery: URL

    @Value("classpath:graphql/delete-championnat.graphql")
    private lateinit var deleteChampionnatMutation: URL

    @Value("classpath:graphql/generate-calendrier.graphql")
    private lateinit var generateCalendrierMutation: URL

    @Value("classpath:graphql/get-saison.graphql")
    private lateinit var getSaisonQuery: URL

    init {
        When("l'utilisateur crée/modifie le(s) championnat(s) avec les informations suivantes") { data: DataTable ->
            data.asMaps().forEach {
                val result: ChampionnatDto = this.graphqlTemplate.post(newChampionnatMutation, it).pluck("championnat")
                scenarioContext.put(LAST_CHAMPIONNAT_ID, result.id!!)
            }
        }

        When("l'utilisateur supprime le championnat avec l'id {string}") { championnatId: String ->
            this.graphqlTemplate.post(deleteChampionnatMutation, mapOf("id" to championnatId))
        }

        When("l'utilisateur génère le calendrier du championnat {string} commençant le {string}") { championnatId: String, dateDebut: String ->
            this.graphqlTemplate.post(generateCalendrierMutation,
                    mapOf("championnatId" to championnatId,
                            "dateDebut" to dateDebut)
            )
        }

        Then("l'utilisateur retrouve les championnats suivants dans la liste des championnats") { expectedChampionnatPayload: String ->
            val championnats: List<ChampionnatDto> = this.graphqlTemplate.post(getChampionnatQuery).pluck("championnats")

            val expectedChampionnat: List<ChampionnatDto> = expectedChampionnatPayload.extractExpected(scenarioContext::replacePlaceHolders)

            assertThat(championnats).containsAll(expectedChampionnat)
        }

        Then("l'utilisateur ne retrouve aucun des championnats suivants dans la liste des championnats") { data: DataTable ->
            val championnats: List<ChampionnatDto> = this.graphqlTemplate.post(getChampionnatQuery).pluck("championnats")

            val expectedChampionnatName = data.asList()
            assertThat(championnats.find { it.nom in expectedChampionnatName }).isNull()
        }

        Then("le calendrier de la saison {int} du championnat {string} comporte {int} journées et {int} matchs")
        { saison: Int, championnatId: String, nbJournees: Int, nbMatchs: Int ->
            val saison: SaisonDto = this.graphqlTemplate.post(getSaisonQuery,
                    mapOf("saison" to saison.toString(),
                            "championnatId" to championnatId))
                    .pluck("saison")

            assertThat(saison.journees.size).isEqualTo(nbJournees)
            assertThat(saison.journees.flatMap { it.matchs }.size).isEqualTo(nbMatchs)
        }
    }
}
