package fr.pk.championshipmanagerapplication.query

import com.expediagroup.graphql.spring.operations.Query
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.springframework.stereotype.Component

@Component
class ChampionnatQuery(private val championnatService: ChampionnatService) : Query {

    /**
     * Query for getting all championnats.
     */
    fun championnats(): List<ChampionnatDto> {
        return championnatService.getAllChampionnats().map { ChampionnatDto(it.id, it.nom) }
    }

    /**
     * Query for getting championnat based on his id.
     */
    fun championnat(id: Long): ChampionnatDto {
        val championnat = championnatService.getChampionnatById(id)
        return ChampionnatDto(championnat.id, championnat.nom)
    }
}
