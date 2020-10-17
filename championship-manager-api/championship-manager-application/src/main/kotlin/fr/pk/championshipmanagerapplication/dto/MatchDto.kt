package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.championnat.JoueurStat
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.equipe.Equipe
import java.time.LocalDateTime

data class MatchDto(
        val domicile: EquipeDto,
        val exterieur: EquipeDto,
        val butDomicile: Int? = null,
        val butExterieur: Int? = null,
        val date: String = LocalDateTime.now().toFrDateString(),
        val joueurs: List<JoueurStatDto> = emptyList()
) {
    constructor(match: Match) : this(EquipeDto(match.domicile), EquipeDto(match.exterieur), match.butDomicile, match.butExterieur, match.date.toFrDateString(), match.joueurs.map { JoueurStatDto(it) })
}

fun MatchDto.toMatch(): Match {
    return Match(
            domicile = Equipe(this.domicile.id, this.domicile.nom),
            exterieur = Equipe(this.exterieur.id, this.exterieur.nom),
            date = this.date.toLocalDateTime(),
            butDomicile = this.butDomicile,
            butExterieur = this.butExterieur,
            joueurs = this.joueurs.map { it.toJoueurStat() }
    )
}

fun JoueurStatDto.toJoueurStat(): JoueurStat {
    return JoueurStat(this.joueur.toJoueur(), this.nbButs, this.nbPasses, this.nbCartonsJaunes, this.nbCartonsRouges)
}

data class JoueurStatDto(
        val joueur: JoueurDto,
        val nbButs: Int = 0,
        val nbPasses: Int = 0,
        val nbCartonsJaunes: Int = 0,
        val nbCartonsRouges: Int = 0
) {
    constructor(joueur: JoueurStat) : this(JoueurDto(joueur.joueur), joueur.nbButs, joueur.nbPasses, joueur.nbCartonsJaunes, joueur.nbCartonsRouges)
}

