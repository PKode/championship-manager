package fr.pk.championshipmanagerinfra.configuration

import fr.pk.championshipmanagerdomain.championnat.DomainChampionnatService
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.equipe.DomainEquipeService
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
}
