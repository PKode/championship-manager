package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.joueur.Joueur
import java.time.LocalDateTime

data class Match(
        val domicile: Equipe,
        val exterieur: Equipe,
        val butDomicile: Int? = null,
        val butExterieur: Int? = null,
        val date: LocalDateTime = LocalDateTime.now().withHour(20).withMinute(0).withSecond(0).withNano(0),
        val joueurs: List<JoueurStat> = emptyList()
) {
    infix fun reverseIf(condition: Boolean): Match {
        return if (condition) Match(domicile = this.exterieur, exterieur = this.domicile, date = this.date) else this
    }

    fun retour() = this reverseIf true

    fun at(date: LocalDateTime): Match {
        return this.copy(date = date)
    }
}

data class JoueurStat(
        val joueur: Joueur,
        val nbButs: Int = 0,
        val nbPasses: Int = 0,
        val nbCartonsJaunes: Int = 0,
        val nbCartonsRouges: Int = 0
)
