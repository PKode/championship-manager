package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.port.MatchRepository
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdJoueur
import fr.pk.championshipmanagerinfra.entities.XdJoueurStat
import fr.pk.championshipmanagerinfra.entities.XdMatch
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.addAll
import kotlinx.dnq.query.eq
import kotlinx.dnq.query.filter
import kotlinx.dnq.query.sortedBy
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class XdMatchRepository(private val xdStore: TransientEntityStore) : MatchRepository {
    override fun findAll(): List<Match> {
        return xdStore.transactional {
            XdMatch findAllMapped { it.toMatch() }
        }
    }

    override fun findById(matchId: Int): Match {
        return xdStore.transactional {
            XdMatch.filter { it.id eq matchId }.map { e -> e.toMatch() }.first()
        }
    }

    override fun saveOrUpdate(match: Match): Match {
        return xdStore.transactional {
            val joueurs = match.joueurs.distinctBy { it.joueur.id }.map {
                // TODO: Move into its own repository ?
                XdJoueurStat.saveOrUpdateAndMap(
                        clause = { xdStat -> (xdStat.joueur.id eq it.joueur.id) and (xdStat.matchId eq match.id) },
                        ifNew = { n ->
                            n.joueur = XdJoueur.findFirstByMapped(XdJoueur::id eq it.joueur.id) { it }
                            n.nbButs = it.nbButs
                            n.nbPasses = it.nbPasses
                            n.nbCartonsJaunes = it.nbCartonsJaunes
                            n.nbCartonsRouges = it.nbCartonsRouges
                            n.matchId = match.id!!
                        },
                        ifUpdate = { u ->
                            u.nbButs = it.nbButs
                            u.nbPasses = it.nbPasses
                            u.nbCartonsJaunes = it.nbCartonsJaunes
                            u.nbCartonsRouges = it.nbCartonsRouges
                            u
                        }) { it }
            }
            XdMatch.saveOrUpdateAndMap(
                    clause = { xdMatch: XdMatch -> xdMatch.id eq match.id },
                    ifNew = { n ->
                        n.id = Random().nextInt()
                        n.domicile = XdEquipe.findFirstByMapped(XdEquipe::id eq match.domicile.id) { it }
                        n.exterieur = XdEquipe.findFirstByMapped(XdEquipe::id eq match.exterieur.id) { it }
                        n.butDomicile = match.butDomicile
                        n.butExterieur = match.butExterieur
                        n.date = match.date.toDateTime()
                    },
                    ifUpdate = {
                        it.date = match.date.toDateTime()
                        it.butDomicile = match.butDomicile
                        it.butExterieur = match.butExterieur
                        it.joueurs.clear()
                        it.joueurs.addAll(joueurs)
                        it
                    }) { it.toMatch() }
        }
    }

    override fun findAllByEquipeAndSaison(equipeId: Int, saison: Int): List<Match> {
        return xdStore.transactional {
            XdMatch.filter { it.saison eq saison }
                    .filter { ((it.domicile.id eq equipeId) or (it.exterieur.id eq equipeId)) }
                    .map { it.toMatch() }
        }
    }

    // TODO: rework to get the first match without score instead of the last with score
    override fun findCurrentSaisonByEquipe(equipeId: Int): Int {
        return xdStore.transactional {
            XdMatch.filter { ((it.domicile.id eq equipeId) or (it.exterieur.id eq equipeId)) }
                    .filter { ((it.butDomicile eq null) and (it.butExterieur eq null)) }
                    .sortedBy(XdMatch::date, asc = true)
                    .map { it.saison }
                    .firstOrNull() ?: LocalDateTime.now().year // TODO: what to do if no match already played
        }
    }
}
