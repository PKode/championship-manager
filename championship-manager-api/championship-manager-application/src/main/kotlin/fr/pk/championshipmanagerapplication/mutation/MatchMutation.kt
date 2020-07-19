package fr.pk.championshipmanagerapplication.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import fr.pk.championshipmanagerapplication.dto.MatchDto
import org.springframework.stereotype.Component

@Component
class MatchMutation : Mutation {
    fun match(match: MatchDto) : MatchDto{
        TODO()
    }
}
