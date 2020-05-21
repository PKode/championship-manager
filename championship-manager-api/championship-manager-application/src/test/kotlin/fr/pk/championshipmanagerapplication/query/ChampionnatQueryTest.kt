package fr.pk.championshipmanagerapplication.query

import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ChampionnatQueryTest {

    private val championnatService = mock(ChampionnatService::class.java)
    private val query = ChampionnatQuery(championnatService)

    @Nested
    inner class GetQuery {

        @Test
        fun `doit retourner la liste des championnats`() {
            // Given
            val domainList = listOf(
                    Championnat(1L, "Ligue 1"),
                    Championnat(2L, "Ligue 2")
            )

            // When
            `when`(championnatService.getAllChampionnats()).thenReturn(domainList)

            val championnats = query.championnats()

            // Then
            val dtoList = listOf(ChampionnatDto(1L, "Ligue 1"), ChampionnatDto(2L, "Ligue 2"))

            assertThat(championnats).isEqualTo(dtoList)
            verify(championnatService, times(1)).getAllChampionnats()
        }

        @Test
        fun `doit retourner le championnat correspondant a id`() {
            // Given
            val domainChampionnat = Championnat(1L, "Ligue 1")

            // When
            `when`(championnatService.getChampionnatById(1L)).thenReturn(domainChampionnat)

            val championnat = query.championnat(1L)

            // Then
            val expectedDto = ChampionnatDto(1L, "Ligue 1")

            assertThat(championnat).isEqualTo(expectedDto)
            verify(championnatService, times(1)).getChampionnatById(1L)
        }
    }


}
