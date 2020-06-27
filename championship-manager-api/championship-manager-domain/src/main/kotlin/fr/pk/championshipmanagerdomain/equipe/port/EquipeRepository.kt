package fr.pk.championshipmanagerdomain.equipe.port

import fr.pk.championshipmanagerdomain.equipe.Equipe

interface EquipeRepository {
    /**
     * @return la liste des toutes les équipes.
     */
    fun findAll(): List<Equipe>

    /**
     * @param id à chercher.
     * @return l'équipe correspondant.
     */
    fun findById(id: Int): Equipe

    /**
     * Créer une nouvelle équipe.
     * @param nom: le nom de l'équipe à créer.
     * @return la nouvelle équipe.
     */
    fun saveOrUpdate(equipe: Equipe): Equipe

    /**
     * Supprime une équipe.
     * @param id de l' équipe à supprimer.
     */
    fun remove(id: Int): Equipe

    /**
     * Récupère toutes les équipes correspondant au championnat avec l'id
     * @param championnatId
     * @return la liste des équipes.
     */
    fun findAllEquipeByChampionnat(championnatId: Int): List<Equipe>
}
