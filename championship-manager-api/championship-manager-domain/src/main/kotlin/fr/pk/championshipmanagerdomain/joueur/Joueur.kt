package fr.pk.championshipmanagerdomain.joueur

import fr.pk.championshipmanagerdomain.equipe.Equipe
import java.time.LocalDate

data class Joueur(
        val id: Int? = null,
        val nom: String,
        val prenom: String? = null,
        val poste: String,
        val nationalite: String,
        val dateNaissance: LocalDate,
        val taille: Int,
        val poids: Int,
        val equipe: Equipe? = null
)
