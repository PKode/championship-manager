package fr.pk.championshipmanagerapp.blackbox.stepdefs

import com.fasterxml.jackson.module.kotlin.readValue
import fr.pk.championshipmanagerapp.blackbox.*
import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import io.cucumber.datatable.DataTable
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

    @Value("classpath:graphql/update-match.graphql")
    private lateinit var updateMatchMuation: URL

    init {
        When("l'utilisateur génère le calendrier du championnat {string} commençant le {string}") { championnatId: String, dateDebut: String ->
            val calendrier: SaisonDto = this.graphqlTemplate.post(generateCalendrierMutation,
                    mapOf("championnatId" to championnatId,
                            "dateDebut" to dateDebut)
            ).pluck("calendrier")
            scenarioContext.put(ContextKey.LAST_CALENDAR, calendrier)
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
        When("l'utilisateur modifie les matchs suivants") { matchs: DataTable ->
            val currentCalendar = scenarioContext.get(ContextKey.LAST_CALENDAR) as SaisonDto
            matchs.asMaps().forEach { match ->
                val matchToUpdate = currentCalendar.matchs.findByEquipe(match["domicile"], match["exterieur"])
                this.graphqlTemplate.post(updateMatchMuation,
                        mapOf("match" to matchToUpdate.copy(butDomicile = match["butDomicile"]?.toInt(), butExterieur = match["butExterieur"]?.toInt(),
                                joueurs = jacksonObjectMapper.readValue(scenarioContext.replacePlaceHolders(match["joueurs"]?.content()
                                        ?: "[]"))))
                )
            }
        }
    }
}

fun List<MatchDto>.findByEquipe(domicile: String?, exterieur: String?) = this.first { it.domicile.nom == domicile && it.exterieur.nom == exterieur }
