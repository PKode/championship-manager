package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Championnat

interface ChampionnatRepository {
    /**
     * @return la liste des tous les championnats.
     */
    fun findAll() : List<Championnat>

    /**
     * @param id à chercher.
     * @return le championnat correspondant.
     */
    fun findById(id: Int) : Championnat

    /**
     * Créer un nouveau championnat.
     * @param nom: le nom du championnat à créer.
     * @return le nouveau championnat.
     */
    fun saveOrUpdate(championnat: Championnat) : Championnat

    /**
     * Supprime un championnat.
     * @param id du championnat à supprimer.
     */
    fun remove(id: Int) : Championnat
}
