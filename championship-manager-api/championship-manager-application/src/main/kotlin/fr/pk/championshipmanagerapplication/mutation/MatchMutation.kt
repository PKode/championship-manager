package fr.pk.championshipmanagerapplication.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerapplication.dto.toMatch
import fr.pk.championshipmanagerdomain.championnat.port.MatchService
import org.springframework.stereotype.Component

@Component
class MatchMutation(private val matchService: MatchService) : Mutation {
    fun match(match: MatchDto) : MatchDto{
        return MatchDto(matchService.createOrEdit(match.toMatch()))
    }
}
