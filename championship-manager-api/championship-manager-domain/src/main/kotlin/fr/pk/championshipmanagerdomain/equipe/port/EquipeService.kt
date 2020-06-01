package fr.pk.championshipmanagerdomain.equipe.port

import fr.pk.championshipmanagerdomain.equipe.Equipe

interface EquipeService {
    /**
     * @return la liste des toutes les équipes.
     */
    fun getAll(): List<Equipe>

    /**
     * @param id à chercher.
     * @return l'équipe correspondant à l'id.
     */
    fun getById(id: Int): Equipe

    /**
     * Créer une nouvelle équipe.
     */
    fun createOrEdit(equipe: Equipe): Equipe

    /**
     * Supprime une équipe.
     * @param id de l'équipe à supprimer.
     */
    fun delete(id: Int): Equipe
}
