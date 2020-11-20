package fr.pk.championshipmanagerapplication.query

import com.expediagroup.graphql.spring.operations.Query
import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerdomain.championnat.port.MatchService
import org.springframework.stereotype.Component

@Component
class MatchQuery(private val matchService: MatchService) : Query {

    /**
     * @param equipeId
     * @return les matchs de l'équipe pour la saison en cours (ie: la saison du premier match non joué).
     */
    fun matchsByEquipeAndCurrentSaison(equipeId: Int): List<MatchDto> {
        return matchService.getAllMatchsByEquipeForCurrentSaison(equipeId).map { MatchDto(it) }
    }
}
