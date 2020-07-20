package fr.pk.championshipmanagerapplication.query

import fr.pk.championshipmanagerapplication.dto.*
import fr.pk.championshipmanagerdomain.championnat.*
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
    private val OM = Equipe(2, "OM")
    private val OL = Equipe(3, "OL")
    private val ASSE = Equipe(4, "ASSE")

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
            `when`(championnatService.getAll()).thenReturn(domainList)

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
            verify(championnatService, times(1)).getAll()
        }

        @Test
        fun `doit retourner le championnat correspondant a id`() {
            // Given
            val domainChampionnat = Championnat(1, "Ligue 1")

            // When
            `when`(championnatService.getById(1)).thenReturn(domainChampionnat)

            val championnat = query.championnat(1)

            // Then
            val expectedDto = ChampionnatDto(1, "Ligue 1")

            assertThat(championnat).isEqualTo(expectedDto)
            verify(championnatService, times(1)).getById(1)
        }
    }

    @Nested
    inner class ClassementFeature {

        @Test
        fun `doit retourner le classement d un championnat`() {

            val id = 1

            `when`(championnatService.getClassement(id, 2020)).thenReturn(listOf(
                    Classement(PSG, v = 5, n = 1, d = 0, bc = 5, bp = 18, pts = 16, mj = 6, diff = 13),
                    Classement(ASSE, v = 1, n = 3, d = 2, bc = 7, bp = 7, pts = 6, mj = 6, diff = 0),
                    Classement(OL, v = 0, n = 5, d = 1, bc = 12, bp = 9, pts = 5, mj = 6, diff = -3),
                    Classement(OM, v = 0, n = 3, d = 3, bc = 17, bp = 7, pts = 3, mj = 6, diff = -10)
            ))

            val classement = query.classement(id, 2020)

            assertThat(classement).containsExactly(
                    ClassementDto(EquipeDto(PSG), v = 5, n = 1, d = 0, bc = 5, bp = 18, pts = 16, mj = 6, diff = 13),
                    ClassementDto(EquipeDto(ASSE), v = 1, n = 3, d = 2, bc = 7, bp = 7, pts = 6, mj = 6, diff = 0),
                    ClassementDto(EquipeDto(OL), v = 0, n = 5, d = 1, bc = 12, bp = 9, pts = 5, mj = 6, diff = -3),
                    ClassementDto(EquipeDto(OM), v = 0, n = 3, d = 3, bc = 17, bp = 7, pts = 3, mj = 6, diff = -10)
            )
            verify(championnatService, times(1)).getClassement(id, 2020)
        }
    }
}
