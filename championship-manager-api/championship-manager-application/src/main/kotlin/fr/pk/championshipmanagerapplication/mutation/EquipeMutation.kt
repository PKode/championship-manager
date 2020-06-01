package fr.pk.championshipmanagerapplication.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import fr.pk.championshipmanagerapplication.dto.EquipeDto
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService
import org.springframework.stereotype.Component

@Component
class EquipeMutation(private val equipeService: EquipeService) : Mutation {
    /**
     * Mutation for creating a equipe.
     * @param name of the equipe to create.
     * @return equipe freshly created.
     */
    fun equipe(equipe: EquipeDto): EquipeDto {
        val newEquipe = equipeService.createOrEdit(Equipe(id = equipe.id, nom = equipe.nom))
        return EquipeDto(newEquipe.id, newEquipe.nom)
    }

    /**
     * Mutation for deleting a equipe.
     * @param id of the equipe to delete.
     * @return equipe freshly deleted.
     */
    fun deleteEquipe(id: Int): EquipeDto {
        val deleteEquipe = equipeService.delete(id)
        return EquipeDto(deleteEquipe.id, deleteEquipe.nom)
    }
}
