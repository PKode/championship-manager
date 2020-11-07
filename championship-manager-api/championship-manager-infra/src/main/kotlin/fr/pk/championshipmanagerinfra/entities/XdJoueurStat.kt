package fr.pk.championshipmanagerinfra.entities

import fr.pk.championshipmanagerdomain.championnat.JoueurStat
import fr.pk.championshipmanagerinfra.repository.map
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*
import kotlinx.dnq.query.XdQuery

class XdJoueurStat(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdJoueurStat>()

    var joueur by xdLink1(XdJoueur)

    var nbButs by xdIntProp()

    var nbPasses by xdIntProp()

    var nbCartonsJaunes by xdIntProp()

    var nbCartonsRouges by xdIntProp()

    var matchId by xdRequiredIntProp()

    fun toJoueurStat() = JoueurStat(joueur.toJoueur(), nbButs, nbPasses, nbCartonsJaunes, nbCartonsRouges)
}

fun XdQuery<XdJoueurStat>.toJoueursStat() = this.map { it.toJoueurStat() }
