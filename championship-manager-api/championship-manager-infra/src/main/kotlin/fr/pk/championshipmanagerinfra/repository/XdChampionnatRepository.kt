package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.eq
import org.springframework.stereotype.Component

@Component
class XdChampionnatRepository(private val xdStore: TransientEntityStore) : ChampionnatRepository {

    override fun findAll(): List<Championnat> {
        return xdStore.transactional {
            XdChampionnat findAllMapped { Championnat(id = it.id, nom = it.nom) }
        }
    }

    override fun findById(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.findByIdMapped(XdChampionnat::id eq id) { Championnat(it.id, it.nom) }
        }
    }

    override fun saveOrUpdate(championnat: Championnat): Championnat {
        return xdStore.transactional {
            XdChampionnat.saveOrUpdateMapped(XdChampionnat::id eq championnat.id,
                    ifUpdate = { c ->
                        c.nom = championnat.nom
                        c
                    },
                    ifNew = { n ->
                        n.id = Random().nextInt()
                        n.nom = championnat.nom
                    }) {
                Championnat(it.id, it.nom)
            }
        }
    }

    override fun remove(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.removeMapped(XdChampionnat::id eq id) { Championnat(it.id, it.nom) }
        }
    }
}
