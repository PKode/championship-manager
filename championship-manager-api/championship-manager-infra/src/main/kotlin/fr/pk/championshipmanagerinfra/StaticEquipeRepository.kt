package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class StaticEquipeRepository : EquipeRepository {
    val equipes: MutableList<Equipe> = mutableListOf(Equipe(id = 1, nom = "PSG"))

    override fun findAll(): List<Equipe> {
        return equipes
    }

    override fun findById(id: Int): Equipe {
        return equipes.firstOrNull { it.id == id }
                ?: throw NoSuchElementException("Aucune équipe avec l'id :: $id")
    }

    override fun saveOrUpdate(equipe: Equipe): Equipe {
        equipes.firstOrNull { it.id == equipe.id }
                ?.let { equipes.remove(it) }

        val newEquipe = Equipe(id = equipe.id ?: equipes.last().id?.plus(1), nom = equipe.nom)
        equipes.add(newEquipe)
        return newEquipe
    }

    override fun remove(id: Int): Equipe {
        return equipes.firstOrNull { it.id == id }
                .also { equipes.remove(it) }
                ?: throw NoSuchElementException("Aucun championnat avec l'id :: $id à supprimer")
    }
}
