package fr.pk.championshipmanagerapplication.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.EquipeDto
import fr.pk.championshipmanagerdomain.championnat.Championnat
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
        val newEquipe = equipeService.createOrEdit(Equipe(id = equipe.id, nom = equipe.nom,
                championnat = equipe.championnat?.let { Championnat(equipe.championnat.id, equipe.championnat.nom) }))
        return EquipeDto(newEquipe)
    }

    /**
     * Mutation for deleting a equipe.
     * @param id of the equipe to delete.
     * @return equipe freshly deleted.
     */
    fun deleteEquipe(id: Int): EquipeDto {
        val deleteEquipe = equipeService.delete(id)
        return EquipeDto(deleteEquipe)
    }
}
