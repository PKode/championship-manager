package fr.pk.championshipmanagerapp.blackbox.stepdefs

import fr.pk.championshipmanagerapp.blackbox.*
import fr.pk.championshipmanagerapp.blackbox.ContextKey.LAST_CHAMPIONNAT_ID
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.ClassementDto
import fr.pk.championshipmanagerapplication.dto.ClassementJoueurDto
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.ktor.util.*
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

@KtorExperimentalAPI
class ChampionnatStepDefs(private val graphqlClientTest: GraphqlClientTest,
                          private val scenarioContext: ScenarioContext) {

    @Value("classpath:graphql/championnat-mutation.graphql")
    private lateinit var championnatMutation: URL

    @Value("classpath:graphql/championnat-query.graphql")
    private lateinit var championnatQuery: URL

    @Value("classpath:graphql/classement-query.graphql")
    private lateinit var classementQuery: URL

    @Value("classpath:graphql/classement-joueur-query.graphql")
    private lateinit var classementJoueurQuery: URL

    @When("l'utilisateur crée/modifie le(s) championnat(s) avec les informations suivantes")
    fun `creer modifier un ou des championnats`(data: List<ChampionnatDto>) {
        data.forEach {
            val result: ChampionnatDto = this.graphqlClientTest
                    .execute(championnatMutation, "championnat", mapOf("championnat" to it))
                    .pluck("championnat")
            scenarioContext.put(LAST_CHAMPIONNAT_ID, result.id!!)
        }
    }

    @When("l'utilisateur supprime le championnat avec l'id {string}")
    fun `supprimer un championnat avec id`(championnatId: String) {
        this.graphqlClientTest.execute(championnatMutation, "deleteChampionnat", mapOf("id" to scenarioContext.replace(championnatId)))
    }

    @Then("l'utilisateur retrouve les championnats suivants dans la liste des championnats")
    fun `recuperer les championnats expected`(expectedChampionnatPayload: String) {
        val championnats: List<ChampionnatDto> = graphqlClientTest.execute(championnatQuery, "championnats").pluck("championnats")
        val expectedChampionnat: List<ChampionnatDto> = expectedChampionnatPayload.extractExpected(scenarioContext::replace)
        assertThat(championnats).containsAll(expectedChampionnat)
    }

    @Then("l'utilisateur ne retrouve aucun des championnats suivants dans la liste des championnats")
    fun `aucun championnat expected n exsite`(data: DataTable) {
        val championnats: List<ChampionnatDto> = this.graphqlClientTest.execute(championnatQuery,"championnats").pluck("championnats")

        val expectedChampionnatName = data.asList()
        assertThat(championnats.find { it.nom in expectedChampionnatName }).isNull()
    }

    @Then("l'utilisateur affiche le classement de la saison {int} du championnat {string}")
    fun `afficher le classement de la saison dun championnat`(saison: Int, championnatId: String, expectedClassementPayload: String) {
        val classement: List<ClassementDto> = this.graphqlClientTest.execute(classementQuery,"classement",
                mapOf("saison" to saison, "championnatId" to scenarioContext.replace(championnatId))).pluck("classement")

        val expectedClassement: List<ClassementDto> = expectedClassementPayload.extractExpected(scenarioContext::replace)

        assertThat(classement).containsAll(expectedClassement)
    }

    @Then("l'utilisateur affiche le classement des joueurs de la saison {int} du championnat {string}")
    fun `afficher le classement des joueurs dune saison dun championnat`(saison: Int, championnatId: String, expectedClassementPayload: String) {
        val classement: List<ClassementJoueurDto> = this.graphqlClientTest.execute(classementJoueurQuery,"classementJoueur",
                mapOf("saison" to saison, "championnatId" to scenarioContext.replace(championnatId))).pluck("classementJoueur")

        val expectedClassement: List<ClassementJoueurDto> = expectedClassementPayload.extractExpected(scenarioContext::replace)

        assertThat(classement).containsAll(expectedClassement)
    }
}
