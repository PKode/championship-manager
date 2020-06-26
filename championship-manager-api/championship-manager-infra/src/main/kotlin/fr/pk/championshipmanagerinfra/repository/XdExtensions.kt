package fr.pk.championshipmanagerinfra.repository

import jetbrains.exodus.query.NodeBase
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdEntityType
import kotlinx.dnq.query.XdMutableQuery
import kotlinx.dnq.query.singleOrNull
import kotlinx.dnq.query.toList

inline infix fun <reified T : XdEntity, O : Any> XdEntityType<T>.findAllMapped(mapper: (T) -> O): List<O> {
    return this.all().toList().map { mapper.invoke(it) }
}

inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.findByIdMapped(node: NodeBase, mapper: (T) -> O): O {
    return this.all().singleOrNull(node)
            ?.let { mapper.invoke(it) }
            ?: throw NoSuchElementException("Aucun ${T::class.simpleName} matche la condition :: $node")
}

inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.saveOrUpdateMapped(node: NodeBase, ifUpdate: (T) -> (T), crossinline ifNew: (T) -> Unit, mapper: (T) -> O): O {
    val updatedEntity: T = this.all()
            .singleOrNull(node)
            ?.let { ifUpdate.invoke(it) }
            ?: this.new { ifNew.invoke(this) }
    return mapper.invoke(updatedEntity)
}

inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.removeMapped(node: NodeBase, mapper: (T) -> O): O {
    return this.all().singleOrNull(node)
            ?.let {
                val ret = mapper.invoke(it)
                it.delete()
                ret
            }
            ?: throw NoSuchElementException("Aucun ${T::class.simpleName} matche la condition :: $node")
}


inline fun <reified T : XdEntity, O : Any> XdMutableQuery<T>.map(mapper: (T) -> O): List<O> {
    return this.toList().map(mapper)
}
