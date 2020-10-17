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
        val poids: Int,
        val equipe: EquipeDto? = null
) {
    constructor(joueur: Joueur) : this(
            joueur.id,
            joueur.nom,
            joueur.prenom,
            joueur.poste,
            joueur.nationalite,
            joueur.dateNaissance.toFrDateString(),
            joueur.taille,
            joueur.poids,
            joueur.equipe?.let { EquipeDto(it) }
    )
}

fun JoueurDto.toJoueur(): Joueur {
    return Joueur(this.id, this.nom, this.prenom, this.poste, this.nationalite, this.dateNaissance.toLocalDate(), this.taille, this.poids)
}

