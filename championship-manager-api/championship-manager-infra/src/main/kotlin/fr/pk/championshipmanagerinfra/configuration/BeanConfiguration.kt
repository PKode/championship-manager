package fr.pk.championshipmanagerinfra.configuration

import fr.pk.championshipmanagerdomain.championnat.DomainChampionnatService
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.equipe.DomainEquipeService
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdModel
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class BeanConfiguration {

    @Bean
    fun championnatService(championnatRepository: ChampionnatRepository): ChampionnatService {
        return DomainChampionnatService(championnatRepository)
    }

    @Bean
    fun equipeService(equipeRepository: EquipeRepository): EquipeService {
        return DomainEquipeService(equipeRepository)
    }

    @Bean
    fun xodusStore(): TransientEntityStore {
        XdModel.scanJavaClasspath()

        // Initialize Xodus persistent storage
        val xodusStore = StaticStoreContainer.init(
                dbFolder = File(System.getProperty("user.home"), "championshipmanager"),
                environmentName = "db"
        )

        // Initialize Xodus-DNQ metadata
        initMetaData(XdModel.hierarchy, xodusStore)

        return xodusStore
    }
}
