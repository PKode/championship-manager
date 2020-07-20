package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Match
import java.time.LocalDateTime

interface MatchRepository {

    /**
     * @return la liste des tous les matchs.
     */
    fun findAll(): List<Match>

    /**
     * @return le match correspondant.
     */
    fun findByEquipesIdsAndDate(idDomicile: Int, idExterieur: Int, date: LocalDateTime): Match

    /**
     * Créer un nouveau match.
     * @param match: le match à créer.
     * @return le nouveau match.
     */
    fun saveOrUpdate(match: Match): Match
}