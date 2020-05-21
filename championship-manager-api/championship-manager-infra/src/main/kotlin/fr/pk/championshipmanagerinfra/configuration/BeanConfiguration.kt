package fr.pk.championshipmanagerinfra.configuration

import fr.pk.championshipmanagerdomain.championnat.DomainChampionnatService
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun championnatService(championnatRepository: ChampionnatRepository): ChampionnatService {
        return DomainChampionnatService(championnatRepository)
    }
}
