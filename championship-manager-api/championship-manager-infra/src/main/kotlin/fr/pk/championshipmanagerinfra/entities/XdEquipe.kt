package fr.pk.championshipmanagerinfra.entities

import fr.pk.championshipmanagerdomain.equipe.Equipe
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*

class XdEquipe(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdEquipe>()

    var id by xdRequiredIntProp(unique = true)

    var nom by xdRequiredStringProp()

    var championnat by xdLink0_1(XdChampionnat)

    fun toEquipe() = Equipe(id = this.id, nom = this.nom, championnat = this.championnat?.toChampionnat())
}
