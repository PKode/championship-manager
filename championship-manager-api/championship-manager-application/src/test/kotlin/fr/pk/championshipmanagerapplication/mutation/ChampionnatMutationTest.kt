package fr.pk.championshipmanagerapplication.mutation

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
            `when`(championnatService.createOrEdit(championnatToCreate)).thenReturn(Championnat(1, nom))

            val championnat = mutation.championnat(ChampionnatDto(nom = nom))

            assertThat(championnat).isEqualTo(ChampionnatDto(1, nom))
            verify(championnatService, times(1)).createOrEdit(championnatToCreate)
        }
    }

    @Nested
    inner class EditMutation {

        @Test
        fun `doit modifier un championnat`() {

            val nom = "Ligue 1"

            val championnatToCreate = Championnat(id = 3, nom = nom)
            `when`(championnatService.createOrEdit(championnatToCreate)).thenReturn(Championnat(3, nom))

            val championnat = mutation.championnat(ChampionnatDto(id = 3, nom = nom))

            assertThat(championnat).isEqualTo(ChampionnatDto(3, nom))
            verify(championnatService, times(1)).createOrEdit(championnatToCreate)
        }
    }

    @Nested
    inner class RemoveMutation {

        @Test
        fun `doit supprimer un championnat`() {

            val id = 1
            val nom = "Ligue 1"

            `when`(championnatService.delete(id)).thenReturn(Championnat(id, nom))

            val championnat = mutation.deleteChampionnat(id.toInt())

            assertThat(championnat).isEqualTo(ChampionnatDto(1, nom))
            verify(championnatService, times(1)).delete(id)
        }
    }

    @Nested
    inner class CalendrierFeature {
        @Test
        fun `doit retourner le calendrier genere pour la championnat avec id correspondant`() {
            val id = 1

            val dateDebut = "10/07/2020"
            `when`(championnatService.genererCalendrier(id, dateDebut)).thenReturn(Saison(journees = journees()))

            val generatedSaison = mutation.calendrier(id, dateDebut)

            verify(championnatService, times(1)).genererCalendrier(id, dateDebut)
            assertThat(generatedSaison).isEqualTo(SaisonDto(journees = journees().map { JourneeDto(it) }))
        }

        private fun journees(): List<Journee> {
            return listOf(
                    Journee(
                            1,
                            listOf(
                                    Match(1, Equipe(nom = "PSG"), Equipe(nom = "OM"), butDomicile = 5, butExterieur = 0),
                                    Match(2, Equipe(nom = "OM"), Equipe(nom = "PSG"), butDomicile = 0, butExterieur = 3)
                            )
                    )
            )
        }

        private fun journeesDto(): List<JourneeDto> {
            return listOf(
                    JourneeDto(
                            1,
                            listOf(
                                    MatchDto(1, EquipeDto(nom = "PSG"), EquipeDto(nom = "OM"), butDomicile = 5, butExterieur = 0),
                                    MatchDto(2, EquipeDto(nom = "OM"), EquipeDto(nom = "PSG"), butDomicile = 0, butExterieur = 3)
                            )
                    )
            )
        }
    }
}
