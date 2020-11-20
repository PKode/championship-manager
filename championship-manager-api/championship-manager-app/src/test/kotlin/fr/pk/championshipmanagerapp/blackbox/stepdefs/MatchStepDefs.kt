package fr.pk.championshipmanagerapp.blackbox.stepdefs

import fr.pk.championshipmanagerapp.blackbox.ContextKey
import fr.pk.championshipmanagerapp.blackbox.ScenarioContext
import fr.pk.championshipmanagerapp.blackbox.TestGraphQLTemplate
import fr.pk.championshipmanagerapp.blackbox.pluck
import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

class MatchStepDefs(private val graphqlTemplate: TestGraphQLTemplate,
                    private val scenarioContext: ScenarioContext) : En {

    @Value("classpath:graphql/get-matchs-by-equipe-current-saison.graphql")
    private lateinit var getMatchByEquipeCurrentSaison: URL

    init {
        Then("l'utilisateur affiche les matchs de l'équipe {string} pour la saison en cours")
        { equipe: String, expectedMatchs: DataTable ->
            val equipeIdsByName = scenarioContext.get(ContextKey.LAST_EQUIPE_ID_BY_NAME) as Map<String, Int>
            val equipeId = equipeIdsByName[equipe] ?: error("equipe $equipe not found in map $equipeIdsByName")
            val actualMatchs: List<MatchDto> = this.graphqlTemplate.post(getMatchByEquipeCurrentSaison, mapOf("equipeId" to equipeId))
                    .pluck("matchsByEquipeAndCurrentSaison")

            val currentCalendar = scenarioContext.get(ContextKey.LAST_CALENDAR) as SaisonDto

            expectedMatchs.asMaps().forEach { match ->
                val findByEquipe = currentCalendar.matchs.findByEquipe(match["domicile"], match["exterieur"])
                assertThat(actualMatchs).contains(findByEquipe)
            }
        }
    }
}
