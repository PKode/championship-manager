package fr.pk.championshipmanagerapp.blackbox.stepdefs

import fr.pk.championshipmanagerapp.blackbox.*
import fr.pk.championshipmanagerapplication.dto.EquipeDto
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.ktor.util.*
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

@KtorExperimentalAPI
class EquipeStepDefs(private val graphqlClientTest: GraphqlClientTest,
                     private val scenarioContext: ScenarioContext) {

    @Value("classpath:graphql/equipe-mutation.graphql")
    private lateinit var equipeMutation: URL

    @Value("classpath:graphql/equipe-query.graphql")
    private lateinit var equipeQuery: URL


    @When("l'utilisateur crée/modifie les équipes avec les informations suivantes")
    fun `creer modifier une equipe`(data: DataTable) {
        val idsNewEquipeByName = mutableMapOf<String, Int>()
        data.asMaps().forEach {
            val result: EquipeDto = this.graphqlClientTest.execute(equipeMutation, "equipe", scenarioContext.replace(it)).pluck("equipe")
            scenarioContext.put(ContextKey.LAST_EQUIPE_ID, result.id!!)
            idsNewEquipeByName[result.nom] = result.id!!
        }
        scenarioContext.put(ContextKey.LAST_EQUIPE_ID_BY_NAME, idsNewEquipeByName)
    }

    @When("l'utilisateur supprime l'équipe avec l'id {string}")
    fun `supprimer une equipe`(equipeId: String) {
        this.graphqlClientTest.execute(equipeMutation, "deleteEquipe", mapOf("id" to scenarioContext.replace(equipeId)))
    }

    @Then("l'utilisateur affiche les équipes")
    fun `afficher les equipes`(expectedEquipePayload: String) {
        val equipes: List<EquipeDto> = this.graphqlClientTest.execute(equipeQuery, "equipes").pluck("equipes")

        val expectedEquipe: List<EquipeDto> = expectedEquipePayload.extractExpected(scenarioContext::replace)

        assertThat(equipes).containsAll(expectedEquipe)
    }

    @Then("l'utilisateur ne retrouve aucune des équipes suivantes dans la liste des équipes")
    fun `aucune equipe`(data: DataTable) {
        val equipes: List<Map<String, Any>> = this.graphqlClientTest.execute(equipeQuery, "equipes").pluck("equipes")

        val expectedEquipe = data.asMaps()

        expectedEquipe.forEach {
            assertThat(equipes).doesNotContain(it)
        }
    }
}
