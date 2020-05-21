package fr.pk.championshipmanagerapplication

import com.expediagroup.graphql.spring.operations.Query
import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.springframework.stereotype.Component

@Component
class ChampionnatQuery(private val championnatService: ChampionnatService) : Query {

    fun championnats(): List<Championnat> {
        return championnatService.getAllChampionnats()
    }
}
