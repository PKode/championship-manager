package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository

class DomainChampionnatService(private val championnatRepository: ChampionnatRepository,
                               private val equipeRepository: EquipeRepository) : ChampionnatService {
    override fun getAllChampionnats(): List<Championnat> {
        return championnatRepository.findAll()
    }

    override fun getChampionnatById(id: Int): Championnat {
        return championnatRepository.findById(id)
    }

    override fun createOrEditChampionnat(championnat: Championnat): Championnat {
        return championnatRepository.saveOrUpdate(championnat)
    }

    override fun deleteChampionnat(id: Int): Championnat {
        return championnatRepository.remove(id)
    }

    @ExperimentalStdlibApi
    override fun genererCalendrier(championnatId: Int): Saison {
        val equipes = equipeRepository.findAllEquipeByChampionnat(championnatId).shuffled()

        val top = equipes.firstHalf()
        val bottom = equipes.secondHalf()

        top.equalize(bottom)

        val journees = generateSequence(generateJournee(1, top, bottom)) {
            if (it.numero == equipes.size - 1) null
            else generateJournee(it.numero + 1, top, bottom)
        }.toList()

        return Saison(journees = journees + journees.matchsRetour())
    }

    @ExperimentalStdlibApi
    private fun generateJournee(numero: Int, top: MutableList<Equipe>, bottom: MutableList<Equipe>): Journee {
        val journee = Journee(numero, matchForJournee(top, bottom))
        top.add(1, bottom.removeFirst())
        bottom.add(top.removeLast())
        return journee
    }

    private fun matchForJournee(top: MutableList<Equipe>, bottom: MutableList<Equipe>): List<Match> {
        return top.mapIndexed { index, equipe -> Match(domicile = equipe, exterieur = bottom[index]) reverseIf index.even() }
    }
}

private fun MutableList<Equipe>.equalize(bottom: MutableList<Equipe>) {
    listOf(this, bottom).takeIf { this.size != bottom.size }
            ?.minBy { it.size }
            ?.add(Equipe(nom = "Exempt"))
}

private fun List<Equipe>.secondHalf(): MutableList<Equipe> {
    return this.subList(this.size / 2, this.size).toMutableList()
}

private fun List<Equipe>.firstHalf(): MutableList<Equipe> {
    return this.subList(0, this.size / 2).toMutableList()
}

fun Int.even() = this % 2 == 0
