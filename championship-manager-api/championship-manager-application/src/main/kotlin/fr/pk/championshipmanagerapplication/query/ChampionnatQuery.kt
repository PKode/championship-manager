package fr.pk.championshipmanagerapplication.query

import com.expediagroup.graphql.spring.operations.Query
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import fr.pk.championshipmanagerapplication.dto.toDto
import fr.pk.championshipmanagerdomain.championnat.Saison
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.springframework.stereotype.Component

@Component
class ChampionnatQuery(private val championnatService: ChampionnatService) : Query {

    /**
     * Query for getting all championnats.
     */
    fun championnats(): List<ChampionnatDto> {
        return championnatService.getAllChampionnats().map { ChampionnatDto(it) }
    }

    /**
     * Query for getting championnat based on his id.
     */
    fun championnat(id: Int): ChampionnatDto {
        val championnat = championnatService.getChampionnatById(id)
        return ChampionnatDto(championnat)
    }
}
