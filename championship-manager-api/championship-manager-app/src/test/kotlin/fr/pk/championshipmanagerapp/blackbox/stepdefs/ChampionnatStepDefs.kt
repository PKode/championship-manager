package fr.pk.championshipmanagerapp.blackbox.stepdefs

import fr.pk.championshipmanagerapp.blackbox.ContextKey.LAST_CHAMPIONNAT_ID
import fr.pk.championshipmanagerapp.blackbox.ScenarioContext
import fr.pk.championshipmanagerapp.blackbox.TestGraphQLTemplate
import fr.pk.championshipmanagerapp.blackbox.extractExpected
import fr.pk.championshipmanagerapp.blackbox.pluck
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.ClassementDto
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

    @Value("classpath:graphql/get-classement.graphql")
    private lateinit var getClassementQuery: URL

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

        Then("l'utilisateur affiche le classement de la saison {int} du championnat {string}") { saison: Int, championnatId: String, expectedClassementPayload: String ->
            val classement: List<ClassementDto> = this.graphqlTemplate.post(getClassementQuery, mapOf("saison" to saison, "championnatId" to championnatId)).pluck("classement")

            val expectedClassement: List<ClassementDto> = expectedClassementPayload.extractExpected(scenarioContext::replacePlaceHolders)

            assertThat(classement).containsAll(expectedClassement)
        }
    }
}
