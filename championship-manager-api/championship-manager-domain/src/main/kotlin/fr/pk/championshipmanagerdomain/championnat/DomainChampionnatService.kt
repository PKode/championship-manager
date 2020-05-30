package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService

class DomainChampionnatService(private val championnatRepository: ChampionnatRepository) : ChampionnatService {
    override fun getAllChampionnats(): List<Championnat> {
        return championnatRepository.findAll()
    }

    override fun getChampionnatById(id: Int): Championnat {
        return championnatRepository.findById(id)
    }

    override fun createChampionnat(nom: String): Championnat {
        return championnatRepository.save(nom)
    }

    override fun deleteChampionnat(id: Int): Championnat {
        return championnatRepository.remove(id)
    }
}
