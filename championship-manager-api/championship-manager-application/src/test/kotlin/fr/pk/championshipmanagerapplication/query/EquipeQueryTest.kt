package fr.pk.championshipmanagerapplication.query

import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.EquipeDto
import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.verify


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EquipeQueryTest {
    private val equipeService = Mockito.mock(EquipeService::class.java)
    private val query = EquipeQuery(equipeService)

    @Nested
    inner class GetQuery {

        @Test
        fun `doit retourner la liste des equipes`() {
            // Given
            val championnat = Championnat(1, "Ligue 1")
            val domainList = listOf(
                    Equipe(1, "PSG", championnat),
                    Equipe(2, "Brest", championnat)
            )

            // When
            Mockito.`when`(equipeService.getAll()).thenReturn(domainList)

            val equipes = query.equipes()

            // Then
            val championnatDto = ChampionnatDto(1, "Ligue 1")
            val dtoList = listOf(EquipeDto(1, "PSG", championnatDto), EquipeDto(2, "Brest", championnatDto))

            assertThat(equipes).isEqualTo(dtoList)
            verify(equipeService, Mockito.times(1)).getAll()
        }

        @Test
        fun `doit retourner l'equipe correspondant a id`() {
            // Given
            val domainEquipe = Equipe(1, "Nantes")

            // When
            Mockito.`when`(equipeService.getById(1)).thenReturn(domainEquipe)

            val equipe = query.equipe(1)

            // Then
            val expectedDto = EquipeDto(1, "Nantes")

            assertThat(equipe).isEqualTo(expectedDto)
            verify(equipeService, Mockito.times(1)).getById(1)
        }

        @Test
        fun `doit retourner la liste des equipes dun championnat`() {
            // Given
            val championnat = Championnat(1, "Ligue 1")
            val domainList = listOf(
                    Equipe(1, "PSG", championnat),
                    Equipe(2, "Brest", championnat)
            )

            // When
            Mockito.`when`(equipeService.getEquipesByChampionnat(1)).thenReturn(domainList)

            val equipes = query.equipesOfChampionnat(1)

            // Then
            val championnatDto = ChampionnatDto(1, "Ligue 1")
            val dtoList = listOf(EquipeDto(1, "PSG", championnatDto), EquipeDto(2, "Brest", championnatDto))

            assertThat(equipes).isEqualTo(dtoList)
            verify(equipeService, Mockito.times(1)).getEquipesByChampionnat(1)
        }
    }
}
