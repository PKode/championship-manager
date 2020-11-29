package fr.pk.championshipmanagerdomain.joueur.port

import fr.pk.championshipmanagerdomain.joueur.Joueur

interface JoueurService {
    /**
     * @return la liste des tous les joueurs.
     */
    fun getAll(): List<Joueur>

    /**
     * @param id à chercher.
     * @return le joueur correspondant à l'id.
     */
    fun getById(id: Int): Joueur

    /**
     * Créer un nouveau joueur.
     */
    fun createOrEdit(joueur: Joueur): Joueur

    /**
     * Supprime un joueur.
     * @param id du joueur à supprimer.
     */
    fun delete(id: Int): Joueur

    /**
     * @param equipeId id de l'équipe.
     * @return la liste de tous les joueurs de l'équipe correspondant à l'id.
     */
    fun getJoueursByEquipe(equipeId: Int?): List<Joueur>

    /**
     * Transfert un joueur de l'équipe
     * @param fromEquipeId
     * à l'équipe
     * @param toEquipeId
     */
    fun transfert(joueurId: Int, toEquipeId: Int?): Joueur
}
