package fr.pk.championshipmanagerdomain.joueur.port

import fr.pk.championshipmanagerdomain.joueur.Joueur

interface JoueurRepository {
    /**
     * @return la liste des tous les joueurs.
     */
    fun findAll(): List<Joueur>

    /**
     * @param id à chercher.
     * @return le joueur correspondant.
     */
    fun findById(id: Int): Joueur

    /**
     * Créer un nouveau joueur.
     * @param nom: le nom du joueur à créer.
     * @return le nouveau joueur.
     */
    fun saveOrUpdate(joueur: Joueur): Joueur

    /**
     * Supprime un joueur.
     * @param id du joueur à supprimer.
     */
    fun remove(id: Int): Joueur


    /**
     * @param equipeId id de l'équipe.
     * @return la liste des joueurs de l'équipe correspondant à l'id.
     */
    fun findAllJoueursByEquipe(equipeId: Int): List<Joueur>

    /**
     * Change l'équipe du joueur
     * @param joueurId
     * @param toEquipeId
     */
    fun updateEquipe(joueurId: Int, toEquipeId: Int): Joueur
}
