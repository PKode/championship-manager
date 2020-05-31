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
    inner class CreateMutation {

        @Test
        fun `doit creer un nouveau championnat`() {

            val nom = "Ligue 1"

            val championnatToCreate = Championnat(nom = nom)
            `when`(championnatService.createOrEditChampionnat(championnatToCreate)).thenReturn(Championnat(1, nom))

            val championnat = mutation.championnat(ChampionnatDto(nom = nom))

            assertThat(championnat).isEqualTo(ChampionnatDto(1, nom))
            verify(championnatService, times(1)).createOrEditChampionnat(championnatToCreate)
        }
    }

    @Nested
    inner class EditMutation {

        @Test
        fun `doit modifier un championnat`() {

            val nom = "Ligue 1"

            val championnatToCreate = Championnat(id = 3, nom = nom)
            `when`(championnatService.createOrEditChampionnat(championnatToCreate)).thenReturn(Championnat(3, nom))

            val championnat = mutation.championnat(ChampionnatDto(id = 3, nom = nom))

            assertThat(championnat).isEqualTo(ChampionnatDto(3, nom))
            verify(championnatService, times(1)).createOrEditChampionnat(championnatToCreate)
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
