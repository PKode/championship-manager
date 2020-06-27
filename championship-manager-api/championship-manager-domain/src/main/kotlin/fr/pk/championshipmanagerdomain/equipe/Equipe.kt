package fr.pk.championshipmanagerdomain.equipe

import fr.pk.championshipmanagerdomain.championnat.Championnat

data class Equipe(val id: Int? = null, val nom: String, val championnat: Championnat? = null)
