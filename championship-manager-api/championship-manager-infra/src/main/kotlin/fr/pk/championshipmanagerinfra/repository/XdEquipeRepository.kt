package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.eq
import org.springframework.stereotype.Component

@Component
class XdEquipeRepository(private val xdStore: TransientEntityStore) : EquipeRepository {

    override fun findAll(): List<Equipe> {
        return xdStore.transactional {
            XdEquipe findAllMapped { Equipe(id = it.id, nom = it.nom) }
        }
    }

    override fun findById(id: Int): Equipe {
        return xdStore.transactional {
            XdEquipe.findByIdMapped(XdEquipe::id eq id) { Equipe(it.id, it.nom) }
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
                Equipe(it.id, it.nom)
            }
        }
    }

    override fun remove(id: Int): Equipe {
        return xdStore.transactional {
            XdEquipe.removeMapped(XdEquipe::id eq id) { Equipe(it.id, it.nom) }
        }
    }
}
