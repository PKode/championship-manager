package fr.pk.championshipmanagerinfra.entities

import fr.pk.championshipmanagerdomain.joueur.Joueur
import jetbrains.exodus.entitystore.Entity
import kotlinx.dnq.*
import java.time.LocalDate

class XdJoueur(entity: Entity) : XdEntity(entity) {
    companion object : XdNaturalEntityType<XdJoueur>()

    var id by xdRequiredIntProp(unique = true)

    var nom by xdRequiredStringProp()

    var prenom by xdStringProp()

    var poste by xdRequiredStringProp()

    var nationalite by xdRequiredStringProp()

    var dateNaissance by xdRequiredDateTimeProp()

    var taille by xdRequiredIntProp()

    var poids by xdRequiredIntProp()

    var equipe by xdLink0_1(XdEquipe)

    fun toJoueur() = Joueur(id = this.id, nom = this.nom, prenom = this.prenom, poste = this.poste,
            nationalite = this.nationalite, dateNaissance = LocalDate.ofEpochDay(this.dateNaissance.millis),
            taille = this.taille, poids = this.poids, equipe = this.equipe?.toEquipeWithoutChampionnat())
}
