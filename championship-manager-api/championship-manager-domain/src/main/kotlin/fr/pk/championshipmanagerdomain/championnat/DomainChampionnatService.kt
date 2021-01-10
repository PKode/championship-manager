package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DomainChampionnatService(private val championnatRepository: ChampionnatRepository,
                               private val equipeRepository: EquipeRepository) : ChampionnatService {
    override fun getAll(): List<Championnat> {
        return championnatRepository.findAll()
    }

    override fun getById(id: Int): Championnat {
        return championnatRepository.findById(id)
    }

    override fun createOrEdit(championnat: Championnat): Championnat {
        return championnatRepository.saveOrUpdate(championnat)
    }

    override fun delete(id: Int): Championnat {
        return championnatRepository.remove(id)
    }

    @ExperimentalStdlibApi
    override fun genererCalendrier(championnatId: Int, dateDebut: String): Saison {
        val equipes = equipeRepository.findAllEquipeByChampionnat(championnatId).shuffled()

        val top = equipes.firstHalf()
        val bottom = equipes.secondHalf()

        top.equalize(bottom)

        val localDateDebut = dateDebut.toLocalDate().atTime(20, 0, 0)

        val journees = generateSequence(generateJournee(1, top, bottom, localDateDebut)) {
            if (it.numero == equipes.size - 1) null
            else generateJournee(it.numero + 1, top, bottom, it.firstMatch().date.plusWeeks(1))
        }.toList()

        val saison = Saison(annee = localDateDebut.year, journees = journees + journees.matchsRetour())
        championnatRepository.saveNewSaison(championnatId, saison)

        return saison
    }

    override fun getClassement(id: Int, saison: Int): List<Classement> {
        val matchs = championnatRepository.findMatchsBySaisonAndChampionnat(id, saison)
        val resultatDomicile = matchs.groupBy { it.domicile }
                .mapValues { it.value.fold(Stat()) { stat, match -> stat + match.statDomicile() } }

        val resultatExterieur = matchs.groupBy { it.exterieur }
                .mapValues { it.value.fold(Stat()) { stat, match -> stat + match.statExterieur() } }

        return resultatDomicile.mapValues { it.value + resultatExterieur.getValue(it.key) }
                .map {
                    Classement(
                            equipe = it.key,
                            v = it.value.nbVictoire,
                            n = it.value.nbNul,
                            d = it.value.nbDefaite,
                            bp = it.value.butMarque,
                            bc = it.value.butPris
                    )
                }.sortedDescending()
    }

    override fun getSaison(id: Int, saison: Int): Saison {
        return championnatRepository.getSaison(id, saison)
    }

    override fun getClassementJoueur(id: Int, saison: Int): List<ClassementJoueur> {
        val matchs = championnatRepository.findMatchsBySaisonAndChampionnat(id, saison)

        return matchs.flatMap { it.joueurs }
                .groupBy { it.joueur }
                .mapValues { it.value.sumInClassementJoueur() }
                .values
                .toList()
    }

    @ExperimentalStdlibApi
    private fun generateJournee(numero: Int, top: MutableList<Equipe>, bottom: MutableList<Equipe>, dateDebut: LocalDateTime): Journee {
        val journee = Journee(numero, matchForJournee(top, bottom, dateDebut))
        top.add(1, bottom.removeFirst())
        bottom.add(top.removeLast())
        return journee
    }

    private fun matchForJournee(top: MutableList<Equipe>, bottom: MutableList<Equipe>, dateDebut: LocalDateTime): List<Match> {
        return top.mapIndexed { index, equipe -> Match(domicile = equipe, exterieur = bottom[index], date = dateDebut) reverseIf index.even() }
    }
}

private fun List<JoueurStat>.sumInClassementJoueur(): ClassementJoueur {
    return ClassementJoueur(
            joueur = this.first().joueur,
            nbButs = this.sumBy { it.nbButs },
            nbPasses = this.sumBy { it.nbPasses },
            nbCartonsJaunes = this.sumBy { it.nbCartonsJaunes },
            nbCartonsRouges = this.sumBy { it.nbCartonsRouges },
            nbMatchs = this.size
    )
}

private fun Match.statDomicile() = Stat(
        nbVictoire = if (this.butDomicile ?: 0 > this.butExterieur ?: 0) 1 else 0,
        nbDefaite = if (this.butDomicile ?: 0 < this.butExterieur ?: 0) 1 else 0,
        nbNul = if (this.butDomicile?.equals(this.butExterieur) == true) 1 else 0,
        butMarque = this.butDomicile ?: 0,
        butPris = this.butExterieur ?: 0
)


private fun Match.statExterieur() = Stat(
        nbVictoire = if (this.butExterieur ?: 0 > this.butDomicile ?: 0) 1 else 0,
        nbDefaite = if (this.butExterieur ?: 0 < this.butDomicile ?: 0) 1 else 0,
        nbNul = if (this.butExterieur?.equals(this.butDomicile) == true) 1 else 0,
        butMarque = this.butExterieur ?: 0,
        butPris = this.butDomicile ?: 0
)

private fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("d/MM/yyyy"))
}

private fun MutableList<Equipe>.equalize(bottom: MutableList<Equipe>) {
    listOf(this, bottom).takeIf { this.size != bottom.size }
            ?.minByOrNull { it.size }
            ?.add(Equipe(nom = "Exempt"))
}

private fun List<Equipe>.secondHalf(): MutableList<Equipe> {
    return this.subList(this.size / 2, this.size).toMutableList()
}

private fun List<Equipe>.firstHalf(): MutableList<Equipe> {
    return this.subList(0, this.size / 2).toMutableList()
}

fun Int.even() = this % 2 == 0
