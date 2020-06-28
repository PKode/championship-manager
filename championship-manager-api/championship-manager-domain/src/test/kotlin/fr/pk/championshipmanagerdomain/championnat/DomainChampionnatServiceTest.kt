package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.internal.bytebuddy.utility.RandomString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DomainChampionnatServiceTest {

    private val repository = mock(ChampionnatRepository::class.java)
    private val equipeRepository = mock(EquipeRepository::class.java)
    private val service = DomainChampionnatService(repository, equipeRepository)

    @Nested
    inner class CalendrierFeature {
        @ExperimentalStdlibApi
        @ParameterizedTest(name = "Genère un calendrier avec {0} équipes")
        @ValueSource(ints = [2, 4, 8, 10, 20, 3, 5, 7, 11])
        fun `doit generer un calendrier`(nbTeam: Int) {
            val championnat = Championnat(id = 1, nom = "Ligue 1")

            `when`(repository.findById(1)).thenReturn(championnat)
            `when`(equipeRepository.findAllEquipeByChampionnat(1)).thenReturn(randomTeam(nbTeam))

            val saison = service.genererCalendrier(1)

            val matchs = saison.journees.flatMap { it.matchs }

            assertThat(saison.journees.size).isEqualTo((nbTeam - 1) * 2)
            assertThat(matchs).containsExactlyInAnyOrderElementsOf(matchs.toSet())
        }
    }

    fun randomTeam(nbTeam: Int): List<Equipe> {
        return (0 until nbTeam).map { Equipe(id = it + 1, nom = RandomString().nextString()) }
    }

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
