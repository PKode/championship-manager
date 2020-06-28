package fr.pk.championshipmanagerapplication.query

import fr.pk.championshipmanagerapplication.dto.*
import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Journee
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.Saison
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.equipe.Equipe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ChampionnatQueryTest {

    private val championnatService = mock(ChampionnatService::class.java)
    private val query = ChampionnatQuery(championnatService)

    private val PSG = Equipe(1, "PSG")
    private val OL = Equipe(2, "OL")

    private val NOW = LocalDateTime.now()
    @Nested
    inner class GetQuery {

        @Test
        fun `doit retourner la liste des championnats`() {
            // Given
            val domainList = listOf(
                    Championnat(1, "Ligue 1", listOf(
                            Saison(2020, listOf(
                                    Journee(1, listOf(
                                            Match(PSG, OL, date = NOW),
                                            Match(OL, PSG, date = NOW)
                                    ))
                            ))
                    )),
                    Championnat(2, "Ligue 2")
            )

            // When
            `when`(championnatService.getAllChampionnats()).thenReturn(domainList)

            val championnats = query.championnats()

            // Then
            val dtoList = listOf(ChampionnatDto(1, "Ligue 1", listOf(
                    SaisonDto(2020, listOf(
                            JourneeDto(1, listOf(
                                    MatchDto(EquipeDto(PSG), EquipeDto(OL), date = NOW.toFrDateString()),
                                    MatchDto(EquipeDto(OL), EquipeDto(PSG), date = NOW.toFrDateString())
                            ))
                    ))
            )), ChampionnatDto(2, "Ligue 2"))

            assertThat(championnats).isEqualTo(dtoList)
            verify(championnatService, times(1)).getAllChampionnats()
        }

        @Test
        fun `doit retourner le championnat correspondant a id`() {
            // Given
            val domainChampionnat = Championnat(1, "Ligue 1")

            // When
            `when`(championnatService.getChampionnatById(1)).thenReturn(domainChampionnat)

            val championnat = query.championnat(1)

            // Then
            val expectedDto = ChampionnatDto(1, "Ligue 1")

            assertThat(championnat).isEqualTo(expectedDto)
            verify(championnatService, times(1)).getChampionnatById(1)
        }
    }


}
