package fr.pk.championshipmanagerdomain.championnat

import java.time.LocalDate

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

