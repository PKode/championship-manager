package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import org.springframework.stereotype.Component
import java.util.*

/**
 * Implements ChampionnatRepository
 */
@Component
class StaticChampionnatRepository : ChampionnatRepository {
    val championnats: MutableList<Championnat> = mutableListOf(Championnat(id = 1, nom = "Ligue 1"))

    override fun findAll(): List<Championnat> {
        return championnats
    }

    override fun findById(id: Int): Championnat {
        return championnats.firstOrNull { it.id == id }
                ?: throw NoSuchElementException("Aucun championnat avec l'id :: $id")
    }

    override fun saveOrUpdate(championnat: Championnat): Championnat {
        championnats.firstOrNull { it.id == championnat.id }
                ?.let { championnats.remove(it) }

        val newChampionnat = Championnat(id = championnat.id ?: championnats.last().id?.plus(1), nom = championnat.nom)
        championnats.add(newChampionnat)
        return newChampionnat
    }

    override fun remove(id: Int): Championnat {
        return championnats.firstOrNull { it.id == id }
                .also { championnats.remove(it) }
                ?: throw NoSuchElementException("Aucun championnat avec l'id :: $id à supprimer")
    }
}
