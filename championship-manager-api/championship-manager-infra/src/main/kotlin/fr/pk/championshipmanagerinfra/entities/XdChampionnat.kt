package fr.pk.championshipmanagerinfra.entities

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Saison
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*
import kotlinx.dnq.query.XdMutableQuery

class XdChampionnat(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdChampionnat>()

    var id by xdRequiredIntProp(unique = true)

    var nom by xdRequiredStringProp()

    val matchs by xdLink0_N(XdMatch)

    fun toChampionnat() = Championnat(id = this.id, nom = this.nom, saisons = matchs.toSaisons())
}
