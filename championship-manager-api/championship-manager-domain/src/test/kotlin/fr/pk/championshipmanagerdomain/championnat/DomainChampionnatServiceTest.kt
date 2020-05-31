package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DomainChampionnatServiceTest {

    private val repository = mock(ChampionnatRepository::class.java)
    private val service = DomainChampionnatService(repository)

    @Nested
    inner class GetFeature {
        @Test
        fun `doit retourner tous les championnats`() {
            val expected = listOf(Championnat(nom = "Ligue 1"), Championnat(nom = "Ligue 2"))

            `when`(repository.findAll()).thenReturn(expected)

            val allChampionnats = service.getAllChampionnats()

            assertThat(allChampionnats).isEqualTo(expected)
        }

        @Test
        fun `doit retourner une liste vide si aucun championnat`() {
            `when`(repository.findAll()).thenReturn(emptyList())

            val allChampionnats = service.getAllChampionnats()

            assertThat(allChampionnats).isEqualTo(emptyList<Championnat>())
        }

        @Test
        fun `doit retourner le championnat correspondant a un id`() {
            val expected = Championnat(id = 1, nom = "Ligue 1")
            `when`(repository.findById(1)).thenReturn(expected)

            val championnat = service.getChampionnatById(1)

            assertThat(championnat).isEqualTo(expected)
        }
    }

    @Nested
    inner class CreateFeature {
        @Test
        fun `doit creer un championnat`() {
            val expectedCreatedChampionnat = Championnat(nom = "Bundesliga")

            `when`(repository.saveOrUpdate(expectedCreatedChampionnat)).thenReturn(expectedCreatedChampionnat)

            val championnat = service.createOrEditChampionnat(expectedCreatedChampionnat)

            assertThat(championnat).isEqualTo(expectedCreatedChampionnat)
        }
    }

    @Nested
    inner class EditFeature {
        @Test
        fun `doit editer un championnat`() {
            val expectedCreatedChampionnat = Championnat(id = 3, nom = "Bundesliga")

            `when`(repository.saveOrUpdate(expectedCreatedChampionnat)).thenReturn(expectedCreatedChampionnat)

            val championnat = service.createOrEditChampionnat(expectedCreatedChampionnat)

            assertThat(championnat).isEqualTo(expectedCreatedChampionnat)
        }
    }

    @Nested
    inner class RemoveFeature {

        @Test
        fun `doit supprimer un championnat correspondant a un id`() {
            val expected = Championnat(id = 1, nom = "Ligue 1")

            `when`(repository.remove(1)).thenReturn(expected)

            val deleteChampionnat = service.deleteChampionnat(1)

            assertThat(deleteChampionnat).isEqualTo(expected)
        }
    }
}
