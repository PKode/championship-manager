package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Match

interface MatchService {
    /**
     * Modifie un nouveau match.
     */
    fun createOrEdit(match: Match): Match

    /**
     * @return tous les matchs de la saison en cours de l'équipe
     * @param equipeId
     */
    fun getAllMatchsByEquipe(equipeId: Int): List<Match>
}
