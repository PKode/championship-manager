package fr.pk.championshipmanagerinfra.entities

import fr.pk.championshipmanagerdomain.championnat.Journee
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.Saison
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*
import kotlinx.dnq.query.XdQuery
import kotlinx.dnq.query.toList
import java.time.LocalDateTime
import java.time.ZoneOffset

class XdMatch(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdMatch>()

    var id by xdRequiredIntProp(unique = true)

    var domicile by xdLink1(XdEquipe)

    var exterieur by xdLink1(XdEquipe)

    var butDomicile by xdNullableIntProp()

    var butExterieur by xdNullableIntProp()

    var journee by xdIntProp()

    var saison by xdIntProp()

    var date by xdRequiredDateTimeProp()

    var championnatId by xdIntProp()

    val joueurs by xdLink0_N(XdJoueurStat)

    fun toMatch(): Match {
        return Match(this.id, this.domicile.toEquipeWithoutChampionnat(), this.exterieur.toEquipeWithoutChampionnat(), this.butDomicile, this.butExterieur,
                LocalDateTime.ofEpochSecond(this.date.millis, 0, ZoneOffset.UTC), joueurs.toJoueursStat())
    }
}


fun XdQuery<XdMatch>.toSaisons(): List<Saison> {
    return this.toList().groupBy { it.saison }
            .map { Saison(it.key, matchByJournee(it.value)) }
}

private fun matchByJournee(matchs: List<XdMatch>) =
        matchs.groupBy { m -> m.journee }.map { j -> Journee(j.key, j.value.toMatchs()) }

private fun List<XdMatch>.toMatchs(): List<Match> {
    return this.map { it.toMatch() }
}
