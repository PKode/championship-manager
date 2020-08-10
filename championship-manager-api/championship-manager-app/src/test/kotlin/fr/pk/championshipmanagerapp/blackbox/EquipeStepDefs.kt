package fr.pk.championshipmanagerapp.blackbox

import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Value
import java.net.URL

class EquipeStepDefs(private val graphqlTemplate: TestGraphQLTemplate,
                     private val scenarioContext: ScenarioContext) : En {

    @Value("classpath:graphql/new-equipe.graphql")
    private lateinit var newEquipeQuery: URL

    @Value("classpath:graphql/get-equipes.graphql")
    private lateinit var getEquipeQuery: URL

    init {
        When("l'utilisateur crée les équipes avec les informations suivantes") { data: DataTable ->
            data.asMaps().forEach {
                this.graphqlTemplate.post(newEquipeQuery, scenarioContext.replacePlaceHolders(it))
            }
        }

        Then("l'utilisateur affiche les équipes") { data: DataTable ->
            val equipes: List<Map<String, Any>> = this.graphqlTemplate.post(getEquipeQuery).pluck("equipes")

            val expectedEquipe = data.asMaps()

            expectedEquipe.forEach {
                assertThat(equipes.contains(it))
            }
            assertThat(equipes.size).isEqualTo(expectedEquipe.size)
        }
    }
}
