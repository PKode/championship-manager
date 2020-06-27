package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.eq
import org.springframework.stereotype.Component

@Component
class XdChampionnatRepository(private val xdStore: TransientEntityStore) : ChampionnatRepository {

    override fun findAll(): List<Championnat> {
        return xdStore.transactional {
            XdChampionnat findAllMapped { it.toChampionnat() }
        }
    }

    override fun findById(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.findFirstByMapped(XdChampionnat::id eq id) { it.toChampionnat() }
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
                it.toChampionnat()
            }
        }
    }

    override fun remove(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.removeMapped(XdChampionnat::id eq id) { it.toChampionnat()}
        }
    }
}