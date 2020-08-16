package fr.pk.championshipmanagerapp.blackbox

import fr.pk.championshipmanagerapplication.dto.SaisonDto
import io.cucumber.java8.En
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

class CalendrierStepDefs(private val graphqlTemplate: TestGraphQLTemplate,
                         private val scenarioContext: ScenarioContext) : En {

    @Value("classpath:graphql/generate-calendrier.graphql")
    private lateinit var generateCalendrierMutation: URL

    @Value("classpath:graphql/get-saison.graphql")
    private lateinit var getSaisonQuery: URL

    init {
        When("l'utilisateur génère le calendrier du championnat {string} commençant le {string}") { championnatId: String, dateDebut: String ->
            this.graphqlTemplate.post(generateCalendrierMutation,
                    mapOf("championnatId" to championnatId,
                            "dateDebut" to dateDebut)
            )
        }

        Then("le calendrier de la saison {int} du championnat {string} comporte {int} journées et {int} matchs")
        { saison: Int, championnatId: String, nbJournees: Int, nbMatchs: Int ->
            val actualSaison: SaisonDto = this.graphqlTemplate.post(getSaisonQuery,
                    mapOf("saison" to saison.toString(),
                            "championnatId" to championnatId))
                    .pluck("saison")

            assertThat(actualSaison.journees.size).isEqualTo(nbJournees)
            assertThat(actualSaison.journees.flatMap { it.matchs }.size).isEqualTo(nbMatchs)
        }
    }
}
