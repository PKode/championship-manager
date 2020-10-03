package fr.pk.championshipmanagerapplication.mutation

import com.expediagroup.graphql.spring.operations.Mutation
import fr.pk.championshipmanagerapplication.dto.JoueurDto
import fr.pk.championshipmanagerapplication.dto.toLocalDate
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.joueur.Joueur
import fr.pk.championshipmanagerdomain.joueur.port.JoueurService
import org.springframework.stereotype.Component

@Component
class JoueurMutation(private val joueurService: JoueurService) : Mutation {

    /**
     * Mutation for creating a joueur.
     * @param name of the joueur to create.
     * @return joueur freshly created.
     */
    fun joueur(joueur: JoueurDto): JoueurDto {
        val newJoueur = joueurService.createOrEdit(
                Joueur(id = joueur.id, nom = joueur.nom, prenom = joueur.prenom, dateNaissance = joueur.dateNaissance.toLocalDate(),
                        poste = joueur.poste, nationalite = joueur.nationalite, taille = joueur.taille, poids = joueur.poids,
                        equipe = joueur.equipe?.let { Equipe(it.id, it.nom) })
        )
        return JoueurDto(newJoueur)
    }

    /**
     * Mutation for deleting a joueur.
     * @param id of the joueur to delete.
     * @return joueur freshly deleted.
     */
    fun deleteJoueur(id: Int): JoueurDto {
        val deleteJoueur = joueurService.delete(id)
        return JoueurDto(deleteJoueur)
    }
}
