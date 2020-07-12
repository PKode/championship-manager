package fr.pk.championshipmanagerdomain.championnat.port

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.Saison

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


    /**
     * Ajoute une nouvelle saison au championnat d'id
     * @param championnatId
     * @return le championnat avec la nouvelle saison.
     */
    fun saveNewSaison(championnatId: Int, saison: Saison): Championnat

    /**
     * Récupère la saison
     * @param saison
     * du championnat
     * @param id
     */
    fun findMatchsBySaisonAndChampionnat(id: Int, saison: Int) : List<Match>
}
