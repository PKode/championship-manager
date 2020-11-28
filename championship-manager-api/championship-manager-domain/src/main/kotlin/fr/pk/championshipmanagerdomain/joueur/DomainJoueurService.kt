package fr.pk.championshipmanagerdomain.joueur

import fr.pk.championshipmanagerdomain.joueur.port.JoueurRepository
import fr.pk.championshipmanagerdomain.joueur.port.JoueurService

class DomainJoueurService(private val joueurRepository: JoueurRepository) : JoueurService {
    override fun getAll(): List<Joueur> {
        return joueurRepository.findAll()
    }

    override fun getById(id: Int): Joueur {
        return joueurRepository.findById(id)
    }

    override fun createOrEdit(joueur: Joueur): Joueur {
        return joueurRepository.saveOrUpdate(joueur)
    }

    override fun delete(id: Int): Joueur {
        return joueurRepository.remove(id)
    }

    override fun getJoueursByEquipe(equipeId: Int): List<Joueur> {
        return joueurRepository.findAllJoueursByEquipe(equipeId)
    }

    override fun transfert(joueurId: Int, toEquipeId: Int): Joueur {
        return joueurRepository.updateEquipe(joueurId, toEquipeId)
    }

}
