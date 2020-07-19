package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Match

interface MatchService {
    /**
     * Modifie un nouveau match.
     */
    fun createOrEditMatch(match: Match): Match
}
