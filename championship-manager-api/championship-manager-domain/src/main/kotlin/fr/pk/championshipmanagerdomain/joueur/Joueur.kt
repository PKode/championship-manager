package fr.pk.championshipmanagerdomain.joueur

import java.time.LocalDate

data class Joueur(
        val id: Int? = null,
        val nom: String,
        val prenom: String,
        val poste: String,
        val nationalite: String,
        val dateNaissance: LocalDate,
        val taille: Int,
        val poids: Int
)
