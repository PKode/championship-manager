package fr.pk.championshipmanagerapplication.query

import fr.pk.championshipmanagerapplication.dto.*
import fr.pk.championshipmanagerdomain.championnat.*
import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatService
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.joueur.Joueur
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import java.time.LocalDate
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

    private val RONALDO = Joueur(nom = "Ronaldo", prenom = "Cristiano", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais")

    @Nested
    inner class GetQuery {

        @Test
        fun `doit retourner la liste des championnats`() {
            // Given
            val domainList = domainChampionnats()

            // When
            `when`(championnatService.getAll()).thenReturn(domainList)

            val championnats = query.championnats()

            // Then
            val dtoList = dtoChampionnats()

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

        @Test
        fun `doit retourner une saison`() {
            // Given
            val domainList = domainChampionnats()

            // When
            `when`(championnatService.getSaison(1, 2020)).thenReturn(domainList.find { it.id == 1 }?.saisons?.find { it.annee == 2020 })

            val saison = query.saison(1, 2020)

            // Then
            val dtoList = dtoChampionnats().find { it.id == 1 }?.saisons?.find { it.annee == 2020 }

            assertThat(saison).isEqualTo(dtoList)
            verify(championnatService, times(1)).getSaison(1, 2020)
        }
    }

    private fun dtoChampionnats(): List<ChampionnatDto> {
        return listOf(ChampionnatDto(1, "Ligue 1", listOf(
                SaisonDto(2020, listOf(
                        JourneeDto(1, listOf(
                                MatchDto(1, EquipeDto(PSG), EquipeDto(OL), date = NOW.toFrDateString()),
                                MatchDto(2, EquipeDto(OL), EquipeDto(PSG), date = NOW.toFrDateString())
                        ))
                ))
        )), ChampionnatDto(2, "Ligue 2"))
    }

    private fun domainChampionnats(): List<Championnat> {
        return listOf(
                Championnat(1, "Ligue 1", listOf(
                        Saison(2020, listOf(
                                Journee(1, listOf(
                                        Match(1, PSG, OL, date = NOW),
                                        Match(2, OL, PSG, date = NOW)
                                ))
                        ))
                )),
                Championnat(2, "Ligue 2")
        )
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

        @Test
        fun `doit retourner le classement des joueurs d un championnat`() {

            val id = 1

            `when`(championnatService.getClassementJoueur(id, 2020)).thenReturn(listOf(
                    ClassementJoueur(RONALDO, 5, 2, nbMatchs = 4)
            ))

            val classement = query.classementJoueur(id, 2020)

            assertThat(classement).containsExactly(
                    ClassementJoueurDto(JoueurDto(RONALDO), 5, 2, nbMatchs = 4)
            )
            verify(championnatService, times(1)).getClassementJoueur(id, 2020)
        }
    }
}
