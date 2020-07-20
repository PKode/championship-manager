package fr.pk.championshipmanagerapplication.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.SaisonDto
import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.springframework.stereotype.Component

@Component
class ChampionnatMutation(private val championnatService: ChampionnatService) : Mutation {

    /**
     * Mutation for creating a championnat.
     * @param name of the championnat to create.
     * @return championnat freshly created.
     */
    fun championnat(championnat: ChampionnatDto): ChampionnatDto {
        val newChampionnat = championnatService.createOrEdit(Championnat(id = championnat.id, nom = championnat.nom))
        return ChampionnatDto(newChampionnat.id, newChampionnat.nom)
    }

    /**
     * Mutation for deleting a championnat.
     * @param id of the championnat to delete.
     * @return championnat freshly deleted.
     */
    fun deleteChampionnat(id: Int): ChampionnatDto {
        val deleteChampionnat = championnatService.delete(id)
        return ChampionnatDto(deleteChampionnat.id, deleteChampionnat.nom)
    }

    /**
     * Génère un calendrier (une saison) pour une championnat donné.
     * @param id du championnat auquel généré le calendrier de la saison.
     * @param date de début du calendrier
     * @return la saison générée.
     */
    fun calendrier(championnatId: Int, dateDebut: String): SaisonDto {
        return SaisonDto(championnatService.genererCalendrier(championnatId, dateDebut))
    }
}
