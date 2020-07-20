package fr.pk.championshipmanagerapplication.mutation

import fr.pk.championshipmanagerapplication.dto.MatchDto
import fr.pk.championshipmanagerapplication.dto.toLocalDateTime
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.port.MatchService
import fr.pk.championshipmanagerdomain.equipe.Equipe
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal class MatchMutationTest {
    private val matchService = Mockito.mock(MatchService::class.java)
    private val mutation = MatchMutation(matchService)

    private val PSG = Equipe(1, "PSG")
    private val CACV = Equipe(2, "CACV")

    @Nested
    inner class CreateOrUpdateMutation {

        @Test
        fun `doit creer ou modifier un nouveau match`() {

            val nom = "PSG"

            val matchToCreate = Match(domicile = PSG, exterieur = CACV, date = "20/07/2020 15:00".toLocalDateTime())
            Mockito.`when`(matchService.createOrEdit(matchToCreate)).thenReturn(matchToCreate)

            val match = mutation.match(MatchDto(matchToCreate))

            Assertions.assertThat(match).isEqualTo(MatchDto(matchToCreate))
            Mockito.verify(matchService, Mockito.times(1)).createOrEdit(matchToCreate)
        }
    }
}
