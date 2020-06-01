package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.equipe.DomainEquipeService
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DomainEquipeServiceTest {

    private val repository = mock(EquipeRepository::class.java)
    private val service = DomainEquipeService(repository)

    @Nested
    inner class GetFeature {
        @Test
        fun `doit retourner toutes les equipes`() {
            val expected = listOf(Equipe(nom = "PSG"), Equipe(nom = "Lille"))

            `when`(repository.findAll()).thenReturn(expected)

            val allChampionnats = service.getAll()

            assertThat(allChampionnats).isEqualTo(expected)
        }

        @Test
        fun `doit retourner une liste vide si aucune equipe`() {
            `when`(repository.findAll()).thenReturn(emptyList())

            val allEquipes = service.getAll()

            assertThat(allEquipes).isEqualTo(emptyList<Equipe>())
        }

        @Test
        fun `doit retourner l equipe correspondant a un id`() {
            val expected = Equipe(id = 1, nom = "PSG")
            `when`(repository.findById(1)).thenReturn(expected)

            val equipe = service.getById(1)

            assertThat(equipe).isEqualTo(expected)
        }
    }

    @Nested
    inner class CreateFeature {
        @Test
        fun `doit creer une equipe`() {
            val expectedCreatedEquipe = Equipe(nom = "Bayern")

            `when`(repository.saveOrUpdate(expectedCreatedEquipe)).thenReturn(expectedCreatedEquipe)

            val equipe = service.createOrEdit(expectedCreatedEquipe)

            assertThat(equipe).isEqualTo(expectedCreatedEquipe)
        }
    }

    @Nested
    inner class EditFeature {
        @Test
        fun `doit editer une equipe`() {
            val expectedCreatedEquipe = Equipe(id = 3, nom = "Dortmund")

            `when`(repository.saveOrUpdate(expectedCreatedEquipe)).thenReturn(expectedCreatedEquipe)

            val equipe = service.createOrEdit(expectedCreatedEquipe)

            assertThat(equipe).isEqualTo(expectedCreatedEquipe)
        }
    }

    @Nested
    inner class RemoveFeature {

        @Test
        fun `doit supprimer une equipe correspondant a un id`() {
            val expected = Equipe(id = 1, nom = "Marseille")

            `when`(repository.remove(1)).thenReturn(expected)

            val deleteEquipe = service.delete(1)

            assertThat(deleteEquipe).isEqualTo(expected)
        }
    }
}
