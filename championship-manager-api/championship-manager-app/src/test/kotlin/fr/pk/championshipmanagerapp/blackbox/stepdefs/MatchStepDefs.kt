package fr.pk.championshipmanagerapp.blackbox.stepdefs

import fr.pk.championshipmanagerapp.blackbox.ContextKey
import fr.pk.championshipmanagerapp.blackbox.GraphqlClientTest
import fr.pk.championshipmanagerapp.blackbox.ScenarioContext
import fr.pk.championshipmanagerapp.blackbox.pluck
import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Then
import io.ktor.util.*
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

@KtorExperimentalAPI
class MatchStepDefs(private val graphqlClientTest: GraphqlClientTest,
                    private val scenarioContext: ScenarioContext) {

    @Value("classpath:graphql/get-matchs-by-equipe-current-saison.graphql")
    private lateinit var getMatchByEquipeCurrentSaison: URL

    @Then("l'utilisateur affiche les matchs de l'équipe {string} pour la saison en cours")
    fun `afficher les matchs d une equipe pour la saison en cours`(equipe: String, expectedMatchs: DataTable) {
        val equipeIdsByName = scenarioContext[ContextKey.LAST_EQUIPE_ID_BY_NAME] as Map<*, *>
        val equipeId = equipeIdsByName[equipe] ?: error("equipe $equipe not found in map $equipeIdsByName")
        val actualMatchs: List<MatchDto> = this.graphqlClientTest.execute(getMatchByEquipeCurrentSaison, "", mapOf("equipeId" to equipeId))
                .pluck("matchsByEquipeAndCurrentSaison")

        val currentCalendar = scenarioContext[ContextKey.LAST_CALENDAR] as SaisonDto

        expectedMatchs.asMaps().forEach { match ->
            val findByEquipe = currentCalendar.matchs.findByEquipe(match["domicile"], match["exterieur"])
            assertThat(actualMatchs).contains(findByEquipe)
        }
    }
}
