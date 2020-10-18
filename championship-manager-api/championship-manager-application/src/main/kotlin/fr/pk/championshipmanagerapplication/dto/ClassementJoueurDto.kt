package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.championnat.ClassementJoueur

data class ClassementJoueurDto(
        val joueur: JoueurDto,
        val nbButs: Int = 0,
        val nbPasses: Int = 0,
        val nbCartonsJaunes: Int = 0,
        val nbCartonsRouges: Int = 0,
        val nbMatchs: Int = 0,
        val ratioBut: Float = nbButs.toFloat() / nbMatchs.toFloat()
) {
    constructor(classementJoueur: ClassementJoueur) : this(
            JoueurDto(classementJoueur.joueur),
            classementJoueur.nbButs,
            classementJoueur.nbPasses,
            classementJoueur.nbCartonsJaunes,
            classementJoueur.nbCartonsRouges,
            classementJoueur.nbMatchs,
            classementJoueur.ratioBut
    )
}
