package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.eq
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
            XdEquipe.findByIdMapped(XdEquipe::id eq id) { it.toEquipe() }
        }
    }

    override fun saveOrUpdate(equipe: Equipe): Equipe {
        return xdStore.transactional {
            XdEquipe.saveOrUpdateMapped(XdEquipe::id eq equipe.id,
                    ifUpdate = { c ->
                        c.nom = equipe.nom
                        c
                    },
                    ifNew = { n ->
                        n.id = Random().nextInt()
                        n.nom = equipe.nom
                    }) {
                it.toEquipe()
            }
        }
    }

    override fun remove(id: Int): Equipe {
        return xdStore.transactional {
            XdEquipe.removeMapped(XdEquipe::id eq id) { it.toEquipe() }
        }
    }
}
