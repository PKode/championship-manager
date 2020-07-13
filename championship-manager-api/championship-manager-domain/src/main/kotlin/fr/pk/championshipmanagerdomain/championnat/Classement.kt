package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.equipe.Equipe

data class Classement(
        val equipe: Equipe,
        val v: Int = 0,
        val n: Int = 0,
        val d: Int = 0,
        val bp: Int = 0,
        val bc: Int = 0,
        val mj: Int = v + n + d,
        val pts: Int = v * 3 + n,
        val diff: Int = bp - bc
) : Comparable<Classement> {
    override fun compareTo(other: Classement): Int {
        val points = this.pts - other.pts
        val diff = this.diff - other.diff
        val bp = this.bp - other.bp

        return if (points == 0) {
            if (diff != 0) diff else bp
        } else points
    }
}

/**
 * Utilisé pour regrouper les donées de chaque équipe dans {@see DomainChampionnatService#genererClassement}
 */
data class Stat(var nbVictoire: Int = 0, var nbNul: Int = 0, var nbDefaite: Int = 0, var butMarque: Int = 0, var butPris: Int = 0) {
    operator fun plus(stat: Stat): Stat {
        return this.apply {
            nbVictoire += stat.nbVictoire
            nbNul += stat.nbNul
            nbDefaite += stat.nbDefaite
            butMarque += stat.butMarque
            butPris += stat.butPris
        }
    }
}
