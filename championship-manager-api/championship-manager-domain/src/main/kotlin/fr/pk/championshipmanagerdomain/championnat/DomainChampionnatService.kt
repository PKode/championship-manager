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

    override fun genererClassement(id: Int, saison: Int): List<Classement> {
        val matchs = championnatRepository.findMatchsBySaisonAndChampionnat(id, saison)
        val resultatDomicile = matchs.groupBy { it.domicile }
                .mapValues { it.value.fold(Stat()) { stat, match -> stat + match.statDomicile() } }

        val resultatExterieur = matchs.groupBy { it.exterieur }
                .mapValues { it.value.fold(Stat()) { stat, match -> stat + match.statExterieur() } }
        // TODO: create a comparator to compare 2 classement (pts, diff, bp, ...)
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
                }.sortedByDescending { it.pts }
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

private fun Match.statDomicile() = Stat(
        nbVictoire = if (this.butDomicile > this.butExterieur) 1 else 0,
        nbDefaite = if (this.butDomicile < this.butExterieur) 1 else 0,
        nbNul = if (this.butDomicile == this.butExterieur) 1 else 0,
        butMarque = this.butDomicile,
        butPris = this.butExterieur
)

private fun Match.statExterieur() = Stat(
        nbVictoire = if (this.butExterieur > this.butDomicile) 1 else 0,
        nbDefaite = if (this.butExterieur < this.butDomicile) 1 else 0,
        nbNul = if (this.butExterieur == this.butDomicile) 1 else 0,
        butMarque = this.butExterieur,
        butPris = this.butDomicile
)

private fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("d/MM/yyyy"))
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

data class Stat(var nbVictoire: Int = 0, var nbNul: Int = 0, var nbDefaite: Int = 0, var butMarque: Int = 0, var butPris: Int = 0) {
    constructor(match: Match) : this(
            nbVictoire = if (match.butDomicile > match.butExterieur) 1 else 0,
            nbDefaite = if (match.butDomicile < match.butExterieur) 1 else 0,
            nbNul = if (match.butDomicile == match.butExterieur) 1 else 0,
            butMarque = match.butDomicile,
            butPris = match.butExterieur
    )

    fun statForDomicile(match: Match) = Stat(
            nbVictoire = if (match.butDomicile > match.butExterieur) 1 else 0,
            nbDefaite = if (match.butDomicile < match.butExterieur) 1 else 0,
            nbNul = if (match.butDomicile == match.butExterieur) 1 else 0,
            butMarque = match.butDomicile,
            butPris = match.butExterieur
    )

    fun statForExterieur(match: Match) = Stat(
            nbVictoire = if (match.butDomicile > match.butExterieur) 1 else 0,
            nbDefaite = if (match.butDomicile < match.butExterieur) 1 else 0,
            nbNul = if (match.butDomicile == match.butExterieur) 1 else 0,
            butMarque = match.butDomicile,
            butPris = match.butExterieur
    )

    operator fun plus(stat: Stat): Stat {
        return this.apply {
            nbVictoire += stat.nbVictoire
            nbNul += stat.nbNul
            nbDefaite += stat.nbDefaite
            butMarque += stat.butMarque
            butPris += stat.butPris
        }
    }
}

data class Classement(val equipe: Equipe, val v: Int = 0, val n: Int = 0, val d: Int = 0, val mj: Int = v + n + d, val pts: Int = v * 3 + n, val bp: Int = 0, val bc: Int = 0, val diff: Int = bp - bc)
