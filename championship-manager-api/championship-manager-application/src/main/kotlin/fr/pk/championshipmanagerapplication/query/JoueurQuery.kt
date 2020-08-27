package fr.pk.championshipmanagerapplication.query

import com.expediagroup.graphql.spring.operations.Query
import fr.pk.championshipmanagerapplication.dto.JoueurDto
import fr.pk.championshipmanagerdomain.joueur.port.JoueurService
import org.springframework.stereotype.Component

@Component
class JoueurQuery(private val joueurService: JoueurService) : Query {

    /**
     * Query for getting all joueurs.
     */
    fun joueurs(): List<JoueurDto> {
        return joueurService.getAll().map { JoueurDto(it) }
    }

    /**
     * Query for getting joueur based on his id.
     */
    fun joueur(id: Int): JoueurDto {
        val joueur = joueurService.getById(id)
        return JoueurDto(joueur)
    }
}
