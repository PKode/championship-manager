package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Classement
import fr.pk.championshipmanagerdomain.championnat.ClassementJoueur
import fr.pk.championshipmanagerdomain.championnat.Saison

interface ChampionnatService {
    /**
     * @return la liste des tous les championnats.
     */
    fun getAll(): List<Championnat>

    /**
     * @param id à chercher.
     * @return le championnat correspondant à l'id.
     */
    fun getById(id: Int): Championnat

    /**
     * Créer un nouveau championnat.
     */
    fun createOrEdit(championnat: Championnat): Championnat

    /**
     * Supprime un championnat.
     * @param id du championnat à supprimer.
     */
    fun delete(id: Int): Championnat

    /**
     * Génère le calendrier de match du championnat correspondant à
     * @param id
     */
    fun genererCalendrier(championnatId: Int, dateDebut: String): Saison

    /**
     * Génère le classement d'une saison d'un championnat.
     * @param id championnat
     * @param saison annee
     */
    fun getClassement(id: Int, saison: Int): List<Classement>

    /**
     * @return une saison.
     * @param id du championnat.
     * @param saison annee de la saison.
     */
    fun getSaison(id: Int, saison: Int): Saison

    /**
     * Génère le classment des buteurs d'une saison d'un championnat.
     * @param id du championnat
     * @param saison
     */
    fun getClassementJoueur(id: Int, saison: Int): List<ClassementJoueur>
}
