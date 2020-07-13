package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.championnat.Classement

data class ClassementDto(
        val equipe: EquipeDto,
        val v: Int = 0,
        val n: Int = 0,
        val d: Int = 0,
        val bp: Int = 0,
        val bc: Int = 0,
        val mj: Int = 0,
        val pts: Int = 0,
        val diff: Int = 0
) {
    constructor(classement: Classement) : this(
            EquipeDto(classement.equipe),
            classement.v,
            classement.n,
            classement.d,
            classement.bp,
            classement.bc,
            classement.mj,
            classement.pts,
            classement.diff
    )
}
