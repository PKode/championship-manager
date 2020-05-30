package fr.pk.championshipmanagerapplication.mutation

import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ChampionnatMutationTest {

    private val championnatService = mock(ChampionnatService::class.java)
    private val mutation = ChampionnatMutation(championnatService)

    @Nested
    inner class NewMutation {

        @Test
        fun `doit creer un nouveau championnat`() {

            val nom = "Ligue 1"

            `when`(championnatService.createChampionnat(nom)).thenReturn(Championnat(1, nom))

            val championnat = mutation.championnat(nom)

            assertThat(championnat).isEqualTo(ChampionnatDto(1, nom))
            verify(championnatService, times(1)).createChampionnat(nom)
        }
    }

    @Nested
    inner class RemoveMutation {

        @Test
        fun `doit supprimer un championnat`() {

            val id = 1
            val nom = "Ligue 1"

            `when`(championnatService.deleteChampionnat(id)).thenReturn(Championnat(id, nom ))

            val championnat = mutation.deleteChampionnat(id.toInt())

            assertThat(championnat).isEqualTo(ChampionnatDto(1, nom))
            verify(championnatService, times(1)).deleteChampionnat(id)
        }
    }
}
