package fr.pk.championshipmanagerinfra.repository

import jetbrains.exodus.query.NodeBase
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdEntityType
import kotlinx.dnq.query.*
import org.joda.time.DateTime
import java.time.LocalDateTime
import java.time.ZoneOffset

inline infix fun <reified T : XdEntity, O : Any> XdEntityType<T>.findAllMapped(mapper: (T) -> O): List<O> {
    return this.all().map { mapper.invoke(it) }
}

inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.findByMapped(node: NodeBase, mapper: (T) -> O): List<O> {
    return this.query(node).map { mapper.invoke(it) }
}

inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.findFirstByMapped(node: NodeBase, mapper: (T) -> O): O {
    return this.findByMapped(node, mapper)
            .firstOrNull()
            ?: throw NoSuchElementException("Aucun ${T::class.simpleName} matche la condition :: $node")
}

inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.saveOrUpdateMapped(node: NodeBase, ifUpdate: (T) -> (T), crossinline ifNew: (T) -> Unit, mapper: (T) -> O): O {
    val updatedEntity: T = this.all()
            .singleOrNull(node)
            ?.let { ifUpdate.invoke(it) }
            ?: this.new { ifNew.invoke(this) }
    return mapper.invoke(updatedEntity)
}

inline fun <reified T : XdEntity, O : Any> XdEntityType<T>.saveOrUpdateAndMap(noinline clause: FilteringContext.(T) -> XdSearchingNode, ifUpdate: (T) -> (T), crossinline ifNew: (T) -> Unit, mapper: (T) -> O): O {
    val updatedEntity: T = this.filter(clause)
            .singleOrNull()
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


inline fun <reified T : XdEntity, O : Any> XdQuery<T>.map(mapper: (T) -> O): List<O> {
    return this.toList().map(mapper)
}

fun LocalDateTime.toDateTime() = DateTime(this.toEpochSecond(ZoneOffset.UTC))
