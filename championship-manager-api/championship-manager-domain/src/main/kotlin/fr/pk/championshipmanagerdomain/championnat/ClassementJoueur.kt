package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.joueur.Joueur

data class ClassementJoueur(
        val joueur: Joueur,
        val nbButs: Int = 0,
        val nbPasses: Int = 0,
        val nbCartonsJaunes: Int = 0,
        val nbCartonsRouges: Int = 0,
        val nbMatchs: Int = 0,
        val ratioBut: Float = nbButs.toFloat() / nbMatchs.toFloat()
)
