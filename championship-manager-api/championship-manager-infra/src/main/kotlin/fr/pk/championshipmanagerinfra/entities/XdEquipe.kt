package fr.pk.championshipmanagerinfra

import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdNaturalEntityType
import kotlinx.dnq.xdRequiredIntProp
import kotlinx.dnq.xdRequiredStringProp

class XdEquipe(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdEquipe>()

    var id by xdRequiredIntProp(unique = true)

    var nom by xdRequiredStringProp()
}
