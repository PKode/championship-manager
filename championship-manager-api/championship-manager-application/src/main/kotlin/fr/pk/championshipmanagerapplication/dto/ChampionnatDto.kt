package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Journee
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.Saison
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ChampionnatDto(val id: Int? = null, val nom: String, val saisons: List<SaisonDto>? = emptyList()) {
    constructor(championnat: Championnat) : this(
            championnat.id,
            championnat.nom,
            championnat.saisons.toDto()
    )
}

data class SaisonDto(
        val annee: Int = LocalDate.now().year,
        val journees: List<JourneeDto> = emptyList()
) {
    constructor(saison: Saison) : this(saison.annee, saison.journees.map { JourneeDto(it) })
}

fun List<Saison>.toDto(): List<SaisonDto> {
    return this.map { SaisonDto(it) }
}

data class JourneeDto(
        val numero: Int,
        val matchs: List<MatchDto> = emptyList()
) {
    constructor(journee: Journee) : this(journee.numero, journee.matchs.map { MatchDto(it) })
}


data class MatchDto(
        val domicile: EquipeDto,
        val exterieur: EquipeDto,
        val butDomicile: Int? = null,
        val butExterieur: Int? = null,
        val date: String = LocalDateTime.now().toFrDateString()
) {
    constructor(match: Match) : this(EquipeDto(match.domicile), EquipeDto(match.exterieur), match.butDomicile, match.butExterieur, match.date.toFrDateString())
}

fun LocalDateTime.toFrDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/YYYY H:mm"))
}
