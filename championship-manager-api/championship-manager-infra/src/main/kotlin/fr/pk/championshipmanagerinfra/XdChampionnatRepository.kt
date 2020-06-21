package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.query.NodeBase
import jetbrains.exodus.util.Random
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdEntityType
import kotlinx.dnq.query.eq
import kotlinx.dnq.query.singleOrNull
import kotlinx.dnq.query.toList
import org.springframework.stereotype.Component


private inline infix fun <reified T : XdEntity, O : Any> XdEntityType<T>.findAllTo(mapper: (T) -> O): List<O> {
    return this.all().toList().map { mapper.invoke(it) }
}

private inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.findByIdTo(node: NodeBase, mapper: (T) -> O): O {
    return this.all().singleOrNull(node)
            ?.let { mapper.invoke(it) }
            ?: throw NoSuchElementException("Aucun ${T::class} avec l'id :: $node")
}

private inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.saveOrUpdateTo(node: NodeBase, ifUpdate: (T) -> (T), crossinline ifNew: (T) -> Unit, mapper: (T) -> O): O {
    val updatedEntity: T = this.all()
            .singleOrNull(node)
            ?.let { ifUpdate.invoke(it) }
            ?: this.new { ifNew.invoke(this) }
    return mapper.invoke(updatedEntity)
}

private inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.removeTo(node: NodeBase, mapper: (T) -> O): O {
    return this.all().singleOrNull(node)
            ?.let {
                val ret = mapper.invoke(it)
                it.delete()
                ret
            }
            ?: throw NoSuchElementException("Aucun ${T::class} avec l'id :: $node")
}

@Component
class XdChampionnatRepository(private val xdStore: TransientEntityStore) : ChampionnatRepository {

    override fun findAll(): List<Championnat> {
        return xdStore.transactional {
            XdChampionnat findAllTo { Championnat(id = it.id, nom = it.nom) }
        }
    }

    override fun findById(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.findByIdTo(XdChampionnat::id eq id) { Championnat(it.id, it.nom) }
        }
    }

    override fun saveOrUpdate(championnat: Championnat): Championnat {
        return xdStore.transactional {
            XdChampionnat.saveOrUpdateTo(XdChampionnat::id eq championnat.id,
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
            XdChampionnat.removeTo(XdChampionnat::id eq id) { Championnat(it.id, it.nom) }
        }
    }
}
