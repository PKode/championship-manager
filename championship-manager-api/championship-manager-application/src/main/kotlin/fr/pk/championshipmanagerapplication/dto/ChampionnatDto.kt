package fr.pk.championshipmanagerapplication.dto

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Journee
import fr.pk.championshipmanagerdomain.championnat.Saison
import java.time.LocalDate

data class ChampionnatDto(val id: Int? = null, val nom: String, val saisons: List<SaisonDto>? = emptyList()) {
    constructor(championnat: Championnat) : this(
            championnat.id,
            championnat.nom,
            championnat.saisons.toDto()
    )
}

data class SaisonDto(
        val annee: Int = LocalDate.now().year,
        val journees: List<JourneeDto> = emptyList(),
        val matchs: List<MatchDto> = journees.flatMap { it.matchs }
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
