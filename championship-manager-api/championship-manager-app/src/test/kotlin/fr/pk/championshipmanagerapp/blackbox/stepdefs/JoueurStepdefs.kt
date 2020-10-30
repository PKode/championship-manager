package fr.pk.championshipmanagerapp.blackbox.stepdefs

import fr.pk.championshipmanagerapp.blackbox.*
import fr.pk.championshipmanagerapplication.dto.JoueurDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Value
import java.net.URL

class JoueurStepdefs(private val graphqlTemplate: TestGraphQLTemplate,
                     private val scenarioContext: ScenarioContext) : En {

    @Value("classpath:graphql/new-joueur.graphql")
    private lateinit var newJoueurMutation: URL

    @Value("classpath:graphql/get-joueur.graphql")
    private lateinit var getJoueurQuery: URL

    @Value("classpath:graphql/get-joueur-by-equipe.graphql")
    private lateinit var getJoueurByEquipeQuery: URL

    @Value("classpath:graphql/delete-joueur.graphql")
    private lateinit var deleteJoueurMutation: URL

    init {
        When("l'utilisateur crée/modifie le(s) joueur(s) avec les informations suivantes") { data: DataTable ->
            data.asMaps().forEach {
                val result: JoueurDto = this.graphqlTemplate.post(newJoueurMutation, it).pluck("joueur")
                scenarioContext.put(ContextKey.LAST_JOUEUR_ID, result.id!!)
            }
        }
        When("l'utilisateur supprime le joueur avec l'id {string}") { joueurId: String ->
            this.graphqlTemplate.post(deleteJoueurMutation, mapOf("id" to joueurId))
        }

        Then("l'utilisateur affiche les joueurs") { expectedJoueurPayload: String ->
            val joueurs: List<JoueurDto> = this.graphqlTemplate.post(getJoueurQuery).pluck("joueurs")

            val expectedJoueur: List<JoueurDto> = expectedJoueurPayload.extractExpected(scenarioContext::replacePlaceHolders)

            Assertions.assertThat(joueurs).containsAll(expectedJoueur)
        }

        Then("l'utilisateur affiche les joueurs de l'équipe") { equipeId: String, expectedJoueurPayload: String ->
            val joueurs: List<JoueurDto> = this.graphqlTemplate.post(getJoueurByEquipeQuery, mapOf("equipeId" to equipeId)).pluck("joueurs")

            val expectedJoueur: List<JoueurDto> = expectedJoueurPayload.extractExpected(scenarioContext::replacePlaceHolders)

            Assertions.assertThat(joueurs).containsAll(expectedJoueur)
        }

        Then("l'utilisateur ne retrouve aucun des joueurs suivants dans la liste des joueurs") { data: DataTable ->
            val joueurs: List<Map<String, Any>> = this.graphqlTemplate.post(getJoueurQuery).pluck("joueurs")

            val expectedJoueur = data.asMaps()

            expectedJoueur.forEach {
                Assertions.assertThat(joueurs).doesNotContain(it)
            }
        }
    }

}
