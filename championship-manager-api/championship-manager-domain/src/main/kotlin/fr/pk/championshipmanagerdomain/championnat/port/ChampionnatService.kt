package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Championnat

interface ChampionnatService {
    /**
     * @return la liste des tous les championnats.
     */
    fun getAllChampionnats(): List<Championnat>

    /**
     * @param id à chercher.
     * @return le championnat correspondant à l'id.
     */
    fun getChampionnatById(id: Long) : Championnat

    /**
     * Créer un nouveau championnat.
     */
    fun createChampionnat(nom: String): Championnat

    /**
     * Supprime un championnat.
     * @param id du championnat à supprimer.
     */
    fun deleteChampionnat(id: Long) : Championnat
}
