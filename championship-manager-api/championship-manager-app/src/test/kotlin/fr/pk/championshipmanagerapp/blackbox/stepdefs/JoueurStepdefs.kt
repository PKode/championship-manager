package fr.pk.championshipmanagerapp.blackbox.stepdefs

import fr.pk.championshipmanagerapp.blackbox.*
import fr.pk.championshipmanagerapplication.dto.JoueurDto
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.ktor.util.*
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Value
import java.net.URL

@KtorExperimentalAPI
class JoueurStepdefs(private val graphqlClientTest: GraphqlClientTest,
                     private val scenarioContext: ScenarioContext) {

    @Value("classpath:graphql/joueur-mutation.graphql")
    private lateinit var joueurMutation: URL

    @Value("classpath:graphql/transfert-joueur.graphql")
    private lateinit var transfertMutation: URL

    @Value("classpath:graphql/joueur-query.graphql")
    private lateinit var joueurQuery: URL


    @When("l'utilisateur crée/modifie le(s) joueur(s) avec les informations suivantes")
    fun `creer modifier joueur`(data: List<JoueurDto>) {
        data.forEach {
            val result: JoueurDto = this.graphqlClientTest.execute(joueurMutation, "joueur", mapOf("joueur" to it)).pluck("joueur")
            scenarioContext.put(ContextKey.LAST_JOUEUR_ID, result.id!!)
        }
    }

    @When("l'utilisateur supprime le joueur avec l'id {string}")
    fun `supprimer un joueur`(joueurId: String) {
        this.graphqlClientTest.execute(joueurMutation, "deleteJoueur", mapOf("id" to scenarioContext.replace(joueurId)))
    }

    @Then("l'utilisateur affiche les joueurs")
    fun `afficher les joueurs`(expectedJoueurPayload: String) {
        val joueurs: List<JoueurDto> = this.graphqlClientTest.execute(joueurQuery, "joueurs").pluck("joueurs")

        val expectedJoueur: List<JoueurDto> = expectedJoueurPayload.extractExpected(scenarioContext::replace)

        Assertions.assertThat(joueurs).containsAll(expectedJoueur)
    }

    @Then("l'utilisateur affiche les joueurs de l'équipe {string}")
    fun `afficher les joueurs dune equipe`(equipeId: String, expectedJoueurPayload: String) {
        val joueurs: List<JoueurDto> = this.graphqlClientTest.execute(joueurQuery, "joueursByEquipe",
                mapOf("equipeId" to scenarioContext.replace(equipeId))).pluck("joueursByEquipe")

        val expectedJoueur: List<JoueurDto> = expectedJoueurPayload.extractExpected(scenarioContext::replace)

        Assertions.assertThat(joueurs).containsAll(expectedJoueur)
    }

    @Then("l'utilisateur ne retrouve aucun des joueurs suivants dans la liste des joueurs")
    fun `aucun joueur`(data: DataTable) {
        val joueurs: List<Map<String, Any>> = this.graphqlClientTest.execute(joueurQuery, "joueurs").pluck("joueurs")

        val expectedJoueur = data.asMaps()

        expectedJoueur.forEach {
            Assertions.assertThat(joueurs).doesNotContain(it)
        }
    }

    @When("l'utilisateur transfert le joueur {string} dans l'équipe {string}")
    fun `transferer joueur`(joueurId: String, equipeId: String) {
        this.graphqlClientTest.execute(transfertMutation, "transfert",
                mapOf("joueurIds" to listOf(scenarioContext.replace(joueurId)), "equipeId" to scenarioContext.replace(equipeId)))
    }
}
