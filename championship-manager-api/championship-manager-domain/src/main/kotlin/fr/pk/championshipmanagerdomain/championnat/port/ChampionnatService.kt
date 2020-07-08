package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Saison
import fr.pk.championshipmanagerdomain.equipe.Equipe

interface ChampionnatService {
    /**
     * @return la liste des tous les championnats.
     */
    fun getAllChampionnats(): List<Championnat>

    /**
     * @param id à chercher.
     * @return le championnat correspondant à l'id.
     */
    fun getChampionnatById(id: Int) : Championnat

    /**
     * Créer un nouveau championnat.
     */
    fun createOrEditChampionnat(championnat: Championnat): Championnat

    /**
     * Supprime un championnat.
     * @param id du championnat à supprimer.
     */
    fun deleteChampionnat(id: Int) : Championnat

    /**
     * Génère le calendrier de match du championnat correspondant à
     * @param id
     */
    fun genererCalendrier(championnatId: Int, dateDebut: String) : Saison
}
