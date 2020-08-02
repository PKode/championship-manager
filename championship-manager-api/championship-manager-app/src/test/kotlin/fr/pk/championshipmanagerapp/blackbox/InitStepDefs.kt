package fr.pk.championshipmanagerapp.blackbox

import fr.pk.championshipmanagerapp.ChampionshipManagerAppApplication
import io.cucumber.java8.En
import io.cucumber.spring.CucumberContextConfiguration
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdModel
import kotlinx.dnq.query.toList
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("test", "blackbox")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = ["spring.main.allow-bean-definition-overriding=true"])
@ContextConfiguration(classes = [ChampionshipManagerAppApplication::class, BlackBoxConfiguration::class])
@CucumberContextConfiguration
class InitStepDefs(private val xdStore: TransientEntityStore) : En {

    init {
        // Supprime toutes les données avant tous les scénarios
        xdStore.transactional {
            XdModel.hierarchy.map { it.value.entityType }
                    .forEach { xd -> xd.all().toList().forEach { it.delete() } }
        }
    }
}
