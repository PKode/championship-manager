package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.Saison
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdMatch
import fr.pk.championshipmanagerinfra.entities.toSaisons
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.addAll
import kotlinx.dnq.query.and
import kotlinx.dnq.query.eq
import kotlinx.dnq.query.query
import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class XdChampionnatRepository(private val xdStore: TransientEntityStore) : ChampionnatRepository {

    override fun findAll(): List<Championnat> {
        return xdStore.transactional {
            XdChampionnat findAllMapped { it.toChampionnat() }
        }
    }

    override fun findById(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.findFirstByMapped(XdChampionnat::id eq id) { it.toChampionnat() }
        }
    }

    override fun saveOrUpdate(championnat: Championnat): Championnat {
        return xdStore.transactional {
            XdChampionnat.saveOrUpdateMapped(XdChampionnat::id eq championnat.id,
                    ifUpdate = { c ->
                        c.nom = championnat.nom
                        c
                    },
                    ifNew = { n ->
                        n.id = Random().nextInt()
                        n.nom = championnat.nom
                    }) {
                it.toChampionnat()
            }
        }
    }

    override fun remove(id: Int): Championnat {
        return xdStore.transactional {
            XdChampionnat.removeMapped(XdChampionnat::id eq id) { it.toChampionnat() }
        }
    }

    override fun saveNewSaison(championnatId: Int, saison: Saison): Championnat {
        return xdStore.transactional {
            val matchs = saison.journees.flatMap { j ->
                j.matchs.map { match ->
                    XdMatch.new {
                        // TODO: Find better way to deal with id in all app
                        this.id = SecureRandom().nextInt()
                        this.championnatId = championnatId
                        this.domicile = XdEquipe.findFirstByMapped(XdEquipe::id eq match.domicile.id) { it }
                        this.exterieur = XdEquipe.findFirstByMapped(XdEquipe::id eq match.exterieur.id) { it }
                        this.journee = j.numero
                        this.saison = saison.annee
                        this.date = match.date.toDateTime()
                        this.butDomicile = match.butDomicile
                        this.butExterieur = match.butExterieur
                    }
                }
            }
            XdChampionnat.saveOrUpdateMapped(XdChampionnat::id eq championnatId,
                    ifUpdate = { c ->
                        c.matchs.addAll(matchs)
                        c
                    },
                    ifNew = { error("Championnat of id $championnatId does not exist") }
            ) {
                it.toChampionnat()
            }
        }
    }

    override fun findMatchsBySaisonAndChampionnat(championnatId: Int, saison: Int): List<Match> {
        return xdStore.transactional {
            XdMatch.findByMapped((XdMatch::championnatId eq championnatId)
                    and (XdMatch::saison eq saison))
            { it.toMatch() }
        }
    }

    override fun getSaison(championnatId: Int, saison: Int): Saison {
        return xdStore.transactional {
            XdMatch.query(
                    (XdMatch::championnatId eq championnatId)
                            and (XdMatch::saison eq saison)
            ).toSaisons().first()
        }
    }
}
