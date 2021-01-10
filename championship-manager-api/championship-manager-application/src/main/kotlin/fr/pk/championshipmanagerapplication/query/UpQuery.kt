package fr.pk.championshipmanagerapplication.query

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class UpQuery : Query {
    /**
     * Use to check api started from ui.
     */
    fun up() = "UP"
}
