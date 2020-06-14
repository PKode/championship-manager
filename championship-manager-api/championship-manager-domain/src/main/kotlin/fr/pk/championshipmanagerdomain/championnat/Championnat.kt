package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.equipe.Equipe
import java.time.LocalDate
import java.time.LocalDateTime

data class Championnat(
        val id: Int? = null,
        val nom: String,
        val equipes: List<Equipe> = emptyList(),
        val saisons: List<Saison> = emptyList()
)

data class Saison(
        val annee: Int = LocalDate.now().year,
        val journees: List<Journee> = emptyList()
)

data class Journee(
        val numero: Int,
        val matchs: List<Match> = emptyList()
)

fun List<Journee>.matchsRetour(): List<Journee> {
    return this.map { Journee(numero = it.numero + this.size, matchs = it.matchs.map { match -> match.retour() }) }
}

data class Match(
        val domicile: Equipe,
        val exterieur: Equipe,
        val butDomicile: Int = 0,
        val butExterieur: Int = 0,
        val date: LocalDateTime = LocalDateTime.now().withHour(20).withMinute(0).withSecond(0).withNano(0)
) {
    val score: String get() = "$butDomicile - $exterieur"

    infix fun reverseIf(condition: Boolean): Match {
        return if (condition) Match(domicile = this.exterieur, exterieur = this.domicile) else this
    }

    fun retour() = this reverseIf true
}
