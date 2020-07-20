package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.MatchRepository
import fr.pk.championshipmanagerdomain.championnat.port.MatchService

class DomainMatchService(private val matchRepository: MatchRepository) : MatchService {
    override fun createOrEdit(match: Match): Match {
        return matchRepository.saveOrUpdate(match)
    }
}
