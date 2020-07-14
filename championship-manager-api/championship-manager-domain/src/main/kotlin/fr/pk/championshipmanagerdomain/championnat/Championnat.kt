package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.equipe.Equipe
import java.time.LocalDate
import java.time.LocalDateTime

data class Championnat(
        val id: Int? = null,
        val nom: String,
        val saisons: List<Saison> = emptyList()
)

data class Saison(
        val annee: Int = LocalDate.now().year,
        val journees: List<Journee> = emptyList()
)

data class Journee(
        val numero: Int,
        val matchs: List<Match> = emptyList()
) {
    fun firstMatch(): Match {
        return matchs.first()
    }
}

fun List<Journee>.matchsRetour(): List<Journee> {
    return this.map { Journee(numero = it.numero + this.size, matchs = it.matchs.map { match -> match.retour().at(match.date.plusWeeks(this.size.toLong())) }) }
}

// TODO: handle not played match (butDomicile/butExterieur nullable ?)
data class Match(
        val domicile: Equipe,
        val exterieur: Equipe,
        val butDomicile: Int? = null,
        val butExterieur: Int? = null,
        val date: LocalDateTime = LocalDateTime.now().withHour(20).withMinute(0).withSecond(0).withNano(0)
) {
    infix fun reverseIf(condition: Boolean): Match {
        return if (condition) Match(domicile = this.exterieur, exterieur = this.domicile, date = this.date) else this
    }

    fun retour() = this reverseIf true

    fun at(date: LocalDateTime): Match {
        return this.copy(date = date)
    }
}
