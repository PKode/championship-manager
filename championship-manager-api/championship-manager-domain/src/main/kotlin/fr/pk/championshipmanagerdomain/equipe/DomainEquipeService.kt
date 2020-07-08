package fr.pk.championshipmanagerdomain.equipe

import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService

class DomainEquipeService(private val equipeRepository: EquipeRepository) : EquipeService {
    override fun getAll(): List<Equipe> {
        return equipeRepository.findAll()
    }

    override fun getById(id: Int): Equipe {
        return equipeRepository.findById(id)
    }

    override fun createOrEdit(equipe: Equipe): Equipe {
        return equipeRepository.saveOrUpdate(equipe)
    }

    override fun delete(id: Int): Equipe {
        return equipeRepository.remove(id)
    }

    override fun getEquipesByChampionnat(championnatId: Int): List<Equipe> {
        return equipeRepository.findAllEquipeByChampionnat(championnatId)
    }
}
