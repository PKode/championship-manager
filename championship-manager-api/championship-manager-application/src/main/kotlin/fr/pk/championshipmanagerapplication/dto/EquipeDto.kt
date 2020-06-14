package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.equipe.Equipe

data class EquipeDto(val id: Int? = null, val nom: String) {
    constructor(equipe: Equipe) : this(equipe.id, equipe.nom)
}
