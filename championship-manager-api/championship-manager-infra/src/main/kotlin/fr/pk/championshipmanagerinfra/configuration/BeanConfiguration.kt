package fr.pk.championshipmanagerinfra.configuration

import fr.pk.championshipmanagerdomain.championnat.DomainChampionnatService
import fr.pk.championshipmanagerdomain.championnat.DomainMatchService
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.championnat.port.MatchRepository
import fr.pk.championshipmanagerdomain.championnat.port.MatchService
import fr.pk.championshipmanagerdomain.equipe.DomainEquipeService
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService
import fr.pk.championshipmanagerdomain.joueur.DomainJoueurService
import fr.pk.championshipmanagerdomain.joueur.port.JoueurRepository
import fr.pk.championshipmanagerdomain.joueur.port.JoueurService
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdModel
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class BeanConfiguration {

    @Value("\${xodus.path}")
    private lateinit var xodusPath: String

    @Value("\${xodus.dbName}")
    private lateinit var xodusName: String

    @Value("\${xodus.environmentName}")
    private lateinit var xodusEnvironmentName: String

    @Bean
    fun championnatService(championnatRepository: ChampionnatRepository, equipeRepository: EquipeRepository): ChampionnatService {
        return DomainChampionnatService(championnatRepository, equipeRepository)
    }

    @Bean
    fun equipeService(equipeRepository: EquipeRepository): EquipeService {
        return DomainEquipeService(equipeRepository)
    }

    @Bean
    fun matchService(matchRepository: MatchRepository): MatchService {
        return DomainMatchService(matchRepository)
    }

    @Bean
    fun joueurService(joueurRepository: JoueurRepository): JoueurService {
        return DomainJoueurService(joueurRepository)
    }

    @Bean
    fun xodusStore(): TransientEntityStore {
        XdModel.scanPackages(arrayOf("fr.pk.championshipmanagerinfra.entities"))

        // Initialize Xodus persistent storage
        val xodusStore = StaticStoreContainer.init(
                dbFolder = File(xodusPath, xodusName),
                environmentName = xodusEnvironmentName
        )

        // Initialize Xodus-DNQ metadata
        initMetaData(XdModel.hierarchy, xodusStore)

        return xodusStore
    }
}
