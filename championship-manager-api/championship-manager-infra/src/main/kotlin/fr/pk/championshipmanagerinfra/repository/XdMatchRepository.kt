package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.port.MatchRepository
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdJoueur
import fr.pk.championshipmanagerinfra.entities.XdJoueurStat
import fr.pk.championshipmanagerinfra.entities.XdMatch
import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.query.addAll
import kotlinx.dnq.query.eq
import kotlinx.dnq.query.filter
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class XdMatchRepository(private val xdStore: TransientEntityStore) : MatchRepository {
    override fun findAll(): List<Match> {
        return xdStore.transactional {
            XdMatch findAllMapped { it.toMatch() }
        }
    }

    override fun findByEquipesIdsAndDate(idDomicile: Int, idExterieur: Int, date: LocalDateTime): Match {
        return xdStore.transactional {
            XdMatch.filter {
                (it.domicile.id eq idDomicile) and (it.exterieur.id eq idExterieur) and (it.date eq date.toDateTime())
            }.map { e -> e.toMatch() }.first()
        }
    }

    override fun saveOrUpdate(match: Match): Match {
        return xdStore.transactional {
            val joueurs = match.joueurs.map {
                XdJoueurStat.new {
                    this.joueur = XdJoueur.findFirstByMapped(XdJoueur::id eq it.joueur.id) { it }
                    this.nbButs = it.nbButs
                    this.nbPasses = it.nbPasses
                    this.nbCartonsJaunes = it.nbCartonsJaunes
                    this.nbCartonsRouges = it.nbCartonsRouges
                }
            }
            XdMatch.saveOrUpdateAndMap(
                    clause = { xdMatch: XdMatch ->
                        (xdMatch.domicile.id eq match.domicile.id) and (xdMatch.exterieur.id eq match.exterieur.id) and (xdMatch.date eq match.date.toDateTime())
                    },
                    ifNew = { n ->
                        //it.championnatId = championnatId
                        n.domicile = XdEquipe.findFirstByMapped(XdEquipe::id eq match.domicile.id) { it }
                        n.exterieur = XdEquipe.findFirstByMapped(XdEquipe::id eq match.exterieur.id) { it }
                        //it.journee = j.numero
                        //it.saison = saison.annee
                        n.date = match.date.toDateTime()
                    },
                    ifUpdate = {
                        it.date = match.date.toDateTime()
                        it.butDomicile = match.butDomicile
                        it.butExterieur = match.butExterieur
                        it.joueurs.addAll(joueurs)
                        it
                    }) { it.toMatch() }
        }
    }
}
