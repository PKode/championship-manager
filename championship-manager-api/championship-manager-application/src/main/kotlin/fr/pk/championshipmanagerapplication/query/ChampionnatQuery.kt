package fr.pk.championshipmanagerapplication.query

import com.expediagroup.graphql.spring.operations.Query
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.ClassementDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.springframework.stereotype.Component

@Component
class ChampionnatQuery(private val championnatService: ChampionnatService) : Query {

    /**
     * Query for getting all championnats.
     */
    fun championnats(): List<ChampionnatDto> {
        return championnatService.getAll().map { ChampionnatDto(it) }
    }

    /**
     * Query for getting championnat based on his id.
     */
    fun championnat(id: Int): ChampionnatDto {
        val championnat = championnatService.getById(id)
        return ChampionnatDto(championnat)
    }

    /**
     * Calcule et retourne le classement de la saison.
     * @param saison du championnat
     * @param championnatId
     */
    fun classement(championnatId: Int, saison: Int): List<ClassementDto> {
        return championnatService.getClassement(championnatId, saison).map { ClassementDto(it) }
    }

    /**
     * Retourne une saison.
     */
    fun saison(championnatId: Int, saison: Int) : SaisonDto {
        return SaisonDto(championnatService.getSaison(championnatId, saison))
    }
}
