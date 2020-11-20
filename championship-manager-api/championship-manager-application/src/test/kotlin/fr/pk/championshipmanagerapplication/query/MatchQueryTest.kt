package fr.pk.championshipmanagerapplication.query

import fr.pk.championshipmanagerapplication.dto.EquipeDto
import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerapplication.dto.toLocalDateTime
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.port.MatchService
import fr.pk.championshipmanagerdomain.equipe.Equipe
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MatchQueryTest {
    private val matchService = Mockito.mock(MatchService::class.java)
    private val query = MatchQuery(matchService)


    private val PSG = Equipe(1, "PSG")
    private val ASSE = Equipe(2, "ASSE")

    @Test
    fun `doit retourner la liste des matchs d une equipe pour la saison en cours`() {

        val domainMatchs = listOf(
                Match(1, PSG, ASSE, 2, 0, date = "30/04/2020 20:00".toLocalDateTime()),
                Match(2, ASSE, PSG, 1, 1, date = "30/10/2020 19:00".toLocalDateTime()),
                Match(3, PSG, ASSE, date = "20/12/2020 15:00".toLocalDateTime())
        )

        val expectedMatchsDto = listOf(
                MatchDto(1, EquipeDto(PSG), EquipeDto(ASSE), 2, 0, date = "30/04/2020 20:00"),
                MatchDto(2, EquipeDto(ASSE), EquipeDto(PSG), 1, 1, date = "30/10/2020 19:00"),
                MatchDto(3, EquipeDto(PSG), EquipeDto(ASSE), date = "20/12/2020 15:00")
        )
        Mockito.`when`(matchService.getAllMatchsByEquipeForCurrentSaison(1)).thenReturn(domainMatchs)

        val matchsByEquipeAndCurrentSaison = query.matchsByEquipeAndCurrentSaison(1)

        Assertions.assertThat(matchsByEquipeAndCurrentSaison).isEqualTo(expectedMatchsDto)
    }
}
