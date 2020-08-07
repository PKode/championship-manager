package fr.pk.championshipmanagerapp.blackbox

import fr.pk.championshipmanagerapplication.dto.EquipeDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
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
            val equipes: List<EquipeDto> = this.graphqlTemplate.post(getEquipeQuery).pluck("equipes")

            val expectedEquipeName = data.asList()
            assertThat(equipes.find { it.nom in expectedEquipeName }).isNotNull
            assertThat(equipes.size).isEqualTo(expectedEquipeName.size)
        }
    }
}
