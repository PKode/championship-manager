package fr.pk.championshipmanagerapp.blackbox.stepdefs

import com.fasterxml.jackson.module.kotlin.readValue
import fr.pk.championshipmanagerapp.blackbox.*
import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.ktor.util.*
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

@KtorExperimentalAPI
class CalendrierStepDefs(private val graphqlClientTest: GraphqlClientTest,
                         private val scenarioContext: ScenarioContext) {

    @Value("classpath:graphql/generate-calendrier.graphql")
    private lateinit var generateCalendrierMutation: URL

    @Value("classpath:graphql/get-saison.graphql")
    private lateinit var getSaisonQuery: URL

    @Value("classpath:graphql/update-match.graphql")
    private lateinit var updateMatchMuation: URL

    @When("l'utilisateur génère le calendrier du championnat {string} commençant le {string}")
    fun `generer calendrier d un championnat`(championnatId: String, dateDebut: String) {
        val calendrier: SaisonDto = this.graphqlClientTest.execute(generateCalendrierMutation, "calendrier",
                mapOf("championnatId" to scenarioContext.replace(championnatId),
                        "dateDebut" to dateDebut)
        ).pluck("calendrier")
        scenarioContext.put(ContextKey.LAST_CALENDAR, calendrier)
    }

    @Then("le calendrier de la saison {int} du championnat {string} comporte {int} journées et {int} matchs")
    fun `verifier calendrier`(saison: Int, championnatId: String, nbJournees: Int, nbMatchs: Int) {

        val actualSaison: SaisonDto = this.graphqlClientTest.execute(getSaisonQuery, "saison",
                mapOf("saison" to saison.toString(),
                        "championnatId" to scenarioContext.replace(championnatId)))
                .pluck("saison")

        assertThat(actualSaison.journees.size).isEqualTo(nbJournees)
        assertThat(actualSaison.journees.flatMap { it.matchs }.size).isEqualTo(nbMatchs)
    }

    @Then("l'utilisateur affiche le calendrier du championnat {string} pour la saison {int}")
    fun `afficher le calendrier d un championnat`(championnatId: String, saison: Int) {

        val actualSaison: SaisonDto = this.graphqlClientTest.execute(getSaisonQuery, "saison",
                mapOf("saison" to saison.toString(),
                        "championnatId" to scenarioContext.replace(championnatId)))
                .pluck("saison")
        scenarioContext.put(ContextKey.LAST_CALENDAR, actualSaison)
    }

    @When("l'utilisateur modifie les matchs suivants")
    fun `modifier matchs`(matchs: DataTable) {
        val currentCalendar = scenarioContext[ContextKey.LAST_CALENDAR] as SaisonDto
        matchs.asMaps().forEach { match ->
            val matchToUpdate = currentCalendar.matchs.findByEquipe(match["domicile"], match["exterieur"])
            val matchUpdated = matchToUpdate.copy(
                    butDomicile = match["butDomicile"]?.toInt(), butExterieur = match["butExterieur"]?.toInt(),
                    joueurs = jacksonObjectMapper.readValue(scenarioContext.replace(match["joueurs"]?.content()
                            ?: "[]")))
            this.graphqlClientTest.execute(updateMatchMuation, "match", mapOf("match" to matchUpdated))
        }
    }
}

fun List<MatchDto>.findByEquipe(domicile: String?, exterieur: String?) = this.first { it.domicile.nom == domicile && it.exterieur.nom == exterieur }
