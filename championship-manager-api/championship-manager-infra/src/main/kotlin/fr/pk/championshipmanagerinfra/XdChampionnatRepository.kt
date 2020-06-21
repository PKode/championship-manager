package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.eq
import kotlinx.dnq.query.singleOrNull
import kotlinx.dnq.query.toList
import org.springframework.stereotype.Component


@Component
class XdChampionnatRepository(private val xdStore: TransientEntityStore) : ChampionnatRepository {
    override fun findAll(): List<Championnat> {
        return xdStore.transactional {
            XdChampionnat.all().toList().map { Championnat(id = it.id, nom = it.nom) }
        }
    }

    override fun findById(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.all().singleOrNull(XdChampionnat::id eq id)
                    ?.let { Championnat(it.id, it.nom) }
                    ?: throw NoSuchElementException("Aucun championnat avec l'id :: $id")
        }
    }

    override fun saveOrUpdate(championnat: Championnat): Championnat {
        return xdStore.transactional {
            val updatedChampionnat: XdChampionnat = XdChampionnat
                    .all()
                    .singleOrNull(XdChampionnat::id eq championnat.id)
                    ?.let {
                        it.nom = championnat.nom
                        it
                    }
                    ?: XdChampionnat.new {
                        this.id = Random().nextInt()
                        this.nom = championnat.nom
                    }
            Championnat(updatedChampionnat.id, updatedChampionnat.nom)
        }
    }

    override fun remove(id: Int): Championnat {
        return xdStore.transactional {

            XdChampionnat.all().singleOrNull(XdChampionnat::id eq id)
                    ?.let {
                        val ret = Championnat(it.id, it.nom)
                        it.delete()
                        ret
                    }
                    ?: throw NoSuchElementException("Aucun championnat avec l'id :: $id")
        }
    }
}
