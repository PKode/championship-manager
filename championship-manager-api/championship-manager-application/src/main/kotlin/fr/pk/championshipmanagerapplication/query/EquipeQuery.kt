package fr.pk.championshipmanagerapplication.query

import com.expediagroup.graphql.spring.operations.Query
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.EquipeDto
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService
import org.springframework.stereotype.Component

@Component
class EquipeQuery(private val equipeService: EquipeService) : Query {

    /**
     * Query for getting all equipes.
     */
    fun equipes(): List<EquipeDto> {
        return equipeService.getAll().map { EquipeDto(it) }
    }

    /**
     * Query for getting equipe based on his id.
     */
    fun equipe(id: Int): EquipeDto {
        val equipe = equipeService.getById(id)
        return EquipeDto(equipe)
    }
}
