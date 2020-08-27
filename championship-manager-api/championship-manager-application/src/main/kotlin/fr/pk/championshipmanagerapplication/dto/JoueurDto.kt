package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.joueur.Joueur

data class JoueurDto(
        val id: Int? = null,
        val nom: String,
        val prenom: String,
        val poste: String,
        val nationalite: String,
        val dateNaissance: String,
        val taille: Int,
        val poids: Int
) {
    constructor(joueur: Joueur) : this(
            joueur.id,
            joueur.nom,
            joueur.prenom,
            joueur.poste,
            joueur.nationalite,
            joueur.dateNaissance.toFrDateString(),
            joueur.taille,
            joueur.poids
    )
}

