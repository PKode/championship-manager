package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Match

interface MatchRepository {

    /**
     * @return la liste des tous les matchs.
     */
    fun findAll(): List<Match>

    /**
     * @return le match correspondant.
     */
    fun findById(matchId: Int): Match

    /**
     * Créer un nouveau match.
     * @param match: le match à créer.
     * @return le nouveau match.
     */
    fun saveOrUpdate(match: Match): Match

    /**
     * @return la liste de tous les matchs d'une équipe pour une saison.
     * @param equipeId
     * @param saison
     */
    fun findAllByEquipeAndSaison(equipeId: Int, saison: Int) : List<Match>

    /**
     * @return le dernier match joué de l'équipe
     * @param equipeId
     */
    fun findCurrentSaisonByEquipe(equipeId: Int): Int
}
