package fr.pk.championshipmanagerinfra.entities

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerinfra.repository.map
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.XdEntity
import kotlinx.dnq.XdNaturalEntityType
import kotlinx.dnq.query.XdMutableQuery
import kotlinx.dnq.xdRequiredIntProp
import kotlinx.dnq.xdRequiredStringProp

class XdEquipe(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdEquipe>()

    var id by xdRequiredIntProp(unique = true)

    var nom by xdRequiredStringProp()

    fun toEquipe() = Equipe(id = this.id, nom = this.nom)

}


fun XdMutableQuery<XdEquipe>.toEquipes(): List<Equipe> {
    return this.map { it.toEquipe() }
}
