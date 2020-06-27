package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.equipe.Equipe

data class EquipeDto(val id: Int? = null, val nom: String, val championnat: ChampionnatDto? = null) {
    constructor(equipe: Equipe) : this(equipe.id, equipe.nom, equipe.championnat?.let { ChampionnatDto(it.id, it.nom) })
}
