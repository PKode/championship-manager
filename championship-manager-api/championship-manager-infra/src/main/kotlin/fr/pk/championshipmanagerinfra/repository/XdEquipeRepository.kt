package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.eq
import kotlinx.dnq.query.filter
import org.springframework.stereotype.Component

@Component
class XdEquipeRepository(private val xdStore: TransientEntityStore) : EquipeRepository {

    override fun findAll(): List<Equipe> {
        return xdStore.transactional {
            XdEquipe findAllMapped { it.toEquipe() }
        }
    }

    override fun findById(id: Int): Equipe {
        return xdStore.transactional {
            XdEquipe.findFirstByMapped(XdEquipe::id eq id) { it.toEquipe() }
        }
    }

    override fun saveOrUpdate(equipe: Equipe): Equipe {
        return xdStore.transactional {
            XdEquipe.saveOrUpdateMapped(XdEquipe::id eq equipe.id,
                    ifUpdate = { c ->
                        c.nom = equipe.nom
                        c.championnat = equipe.championnat?.let {
                            XdChampionnat.findFirstByMapped(XdChampionnat::id eq it.id) { ch -> ch }
                        }
                        c
                    },
                    ifNew = { n ->
                        n.id = Random().nextInt()
                        n.nom = equipe.nom
                        n.championnat = equipe.championnat?.let {
                            XdChampionnat.findFirstByMapped(XdChampionnat::id eq it.id) { c -> c }
                        }
                    }) { it.toEquipe() }
        }
    }

    override fun remove(id: Int): Equipe {
        return xdStore.transactional {
            XdEquipe.removeMapped(XdEquipe::id eq id) { it.toEquipe() }
        }
    }

    override fun findAllEquipeByChampionnat(championnatId: Int): List<Equipe> {
        return xdStore.transactional {
            XdEquipe.filter { it.championnat?.id eq championnatId }.map { e -> e.toEquipe() }
        }
    }
}
