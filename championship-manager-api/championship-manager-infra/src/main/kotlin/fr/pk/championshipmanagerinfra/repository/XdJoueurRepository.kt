package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.joueur.Joueur
import fr.pk.championshipmanagerdomain.joueur.port.JoueurRepository
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdJoueur
import jetbrains.exodus.database.TransientEntityStore
import jetbrains.exodus.util.Random
import kotlinx.dnq.query.eq
import kotlinx.dnq.query.filter
import org.springframework.stereotype.Component

@Component
class XdJoueurRepository(private val xdStore: TransientEntityStore) : JoueurRepository {

    override fun findAll(): List<Joueur> {
        return xdStore.transactional {
            XdJoueur findAllMapped { it.toJoueur() }
        }
    }

    override fun findById(id: Int): Joueur {
        return xdStore.transactional {
            XdJoueur.findFirstByMapped(XdJoueur::id eq id) { it.toJoueur() }
        }
    }

    override fun saveOrUpdate(joueur: Joueur): Joueur {
        return xdStore.transactional {
            XdJoueur.saveOrUpdateMapped(XdJoueur::id eq joueur.id,
                    ifUpdate = { j ->
                        j.nom = joueur.nom
                        j.prenom = joueur.prenom
                        j.poste = joueur.poste
                        j.nationalite = joueur.nationalite
                        j.dateNaissance = joueur.dateNaissance.toDate()
                        j.taille = joueur.taille
                        j.poids = joueur.poids
                        j.equipe = joueur.equipe?.let { XdEquipe.findFirstByMapped(XdEquipe::id eq it.id) { eq -> eq } }
                        j
                    },
                    ifNew = { n ->
                        n.id = Random().nextInt()
                        n.nom = joueur.nom
                        n.prenom = joueur.prenom
                        n.poste = joueur.poste
                        n.nationalite = joueur.nationalite
                        n.dateNaissance = joueur.dateNaissance.toDate()
                        n.taille = joueur.taille
                        n.poids = joueur.poids
                        n.equipe = joueur.equipe?.let { XdEquipe.findFirstByMapped(XdEquipe::id eq it.id) { eq -> eq } }
                    }) {
                it.toJoueur()
            }
        }
    }

    override fun remove(id: Int): Joueur {
        return xdStore.transactional {
            XdJoueur.removeMapped(XdJoueur::id eq id) { it.toJoueur() }
        }
    }

    override fun findAllJoueursByEquipe(equipeId: Int): List<Joueur> {
        return xdStore.transactional {
            XdJoueur.filter { it.equipe?.id eq equipeId }.map { j -> j.toJoueur() }
        }
    }
}

