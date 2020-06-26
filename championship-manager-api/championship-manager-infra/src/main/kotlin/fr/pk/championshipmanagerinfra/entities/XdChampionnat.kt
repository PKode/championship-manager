package fr.pk.championshipmanagerinfra.entities

import fr.pk.championshipmanagerdomain.championnat.Championnat
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*

class XdChampionnat(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdChampionnat>()

    var id by xdRequiredIntProp(unique = true)

    var nom by xdRequiredStringProp()

    val equipes by xdLink0_N(XdEquipe)

    fun toChampionnat() = Championnat(id = this.id, nom = this.nom, equipes = this.equipes.toEquipes())
}
