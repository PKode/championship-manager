package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.ChampionnatRepository
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeRepository
import fr.pk.championshipmanagerdomain.joueur.Joueur
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.internal.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DomainChampionnatServiceTest {

    private val repository = mock(ChampionnatRepository::class.java)
    private val equipeRepository = mock(EquipeRepository::class.java)
    private val service = DomainChampionnatService(repository, equipeRepository)

    val PSG = Equipe(1, "PSG")
    val OM = Equipe(2, "OM")
    val OL = Equipe(3, "OL")
    val ASSE = Equipe(4, "ASSE")

    private val RONALDO = Joueur(nom = "Ronaldo", prenom = "Cristiano", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais")

    private val MESSI = Joueur(nom = "Messi", prenom = "Lionel", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 160, poids = 60, nationalite = "Argentin")

    private val RAMOS = Joueur(nom = "Ramos", prenom = "Sergio", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "DC", taille = 185, poids = 76, nationalite = "Espagnol")

    @Nested
    inner class CalendrierFeature {
        @ExperimentalStdlibApi
        @ParameterizedTest(name = "Genère un calendrier avec {0} équipes")
        @ValueSource(ints = [2, 4, 8, 10, 20, 3, 5, 7, 11])
        fun `doit generer un calendrier`(nbTeam: Int) {
            val championnat = Championnat(id = 1, nom = "Ligue 1")

            `when`(repository.findById(1)).thenReturn(championnat)
            `when`(equipeRepository.findAllEquipeByChampionnat(1)).thenReturn(randomTeam(nbTeam))

            val saison = service.genererCalendrier(1, "17/07/2020")

            val matchs = saison.journees.flatMap { it.matchs }
            val dates = saison.journees.map { it.firstMatch().date }

            assertThat(saison.journees.size).isEqualTo((nbTeam - 1) * 2)
            assertThat(matchs).containsExactlyInAnyOrderElementsOf(matchs.toSet())
            assertThat(dates).containsExactlyInAnyOrderElementsOf(dates.toSet())
        }

        @Test
        fun `doit retourner une saison`() {
            val expectedSaison = championnatWithSaison().saisons.first()
            `when`(repository.getSaison(1, 2020)).thenReturn(expectedSaison)

            val saison = service.getSaison(1, 2020)

            assertThat(saison).isEqualTo(expectedSaison)
        }
    }

    fun randomTeam(nbTeam: Int): List<Equipe> {
        return (0 until nbTeam).map { Equipe(id = it + 1, nom = RandomString().nextString()) }
    }

    @Nested
    inner class ClassementFeature {
        @Test
        fun `doit generer le classement d un championnat`() {
            val championnat = championnatWithSaison()

            `when`(repository.findMatchsBySaisonAndChampionnat(1, 2020)).thenReturn(championnat.saisons.flatMap { s -> s.journees.flatMap { it.matchs } })
            val classement = service.getClassement(1, 2020)
            assertThat(classement).containsExactly(
                    Classement(PSG, v = 5, n = 1, d = 0, bc = 5, bp = 18, pts = 16, mj = 6, diff = 13),
                    Classement(ASSE, v = 1, n = 3, d = 2, bc = 7, bp = 7, pts = 6, mj = 6, diff = 0),
                    Classement(OL, v = 0, n = 5, d = 1, bc = 12, bp = 9, pts = 5, mj = 6, diff = -3),
                    Classement(OM, v = 0, n = 3, d = 3, bc = 17, bp = 7, pts = 3, mj = 6, diff = -10)
            )
        }

        @Test
        fun `doit generer les stats d'un joueur sur une saison d'un championnat`() {
            val championnat = championnatWithSaison()

            `when`(repository.findMatchsBySaisonAndChampionnat(1, 2020)).thenReturn(championnat.saisons.flatMap { s -> s.journees.flatMap { it.matchs } })

            val classementJoueur = service.getClassementJoueur(1, 2020)

            assertThat(classementJoueur).containsExactly(
                    ClassementJoueur(RONALDO, 6, 1, 0, 0, 2, 3.0f),
                    ClassementJoueur(MESSI, 1, 5, 0, 0, 2, 0.5f),
                    ClassementJoueur(RAMOS, 0, 0, 1, 0, 2, 0.0f)
            )
        }
    }

    private fun championnatWithSaison(): Championnat {
        return Championnat(id = 1, nom = "Ligue 1", saisons = listOf(
                Saison(2020, journees = listOf(
                        Journee(1, matchs = listOf(
                                Match(1, PSG, OM, 3, 0, joueurs = listOf(JoueurStat(RONALDO, nbButs = 3), JoueurStat(MESSI, nbPasses = 2), JoueurStat(RAMOS))),
                                Match(2, OL, ASSE, 1, 1)
                        )),
                        Journee(2, matchs = listOf(
                                Match(3, PSG, OL, 4, 1,
                                        joueurs = listOf(JoueurStat(RONALDO, nbButs = 3, nbPasses = 1), JoueurStat(MESSI, nbPasses = 3, nbButs = 1), JoueurStat(RAMOS, nbCartonsJaunes = 1))),
                                Match(4, OM, ASSE, 1, 3)
                        )),
                        Journee(3, matchs = listOf(
                                Match(5, ASSE, PSG, 2, 3),
                                Match(6, OL, OM, 2, 2)
                        )),
                        Journee(4, matchs = listOf(
                                Match(7, OM, PSG, 0, 5),
                                Match(8, ASSE, OL, 0, 0)
                        )),
                        Journee(5, matchs = listOf(
                                Match(9, OL, PSG, 2, 2),
                                Match(10, ASSE, OM, 1, 1)
                        )),
                        Journee(6, matchs = listOf(
                                Match(11, PSG, ASSE, 1, 0),
                                Match(12, OM, OL, 3, 3)
                        ))
                ))
        ))
    }

    @Nested
    inner class GetFeature {
        @Test
        fun `doit retourner tous les championnats`() {
            val expected = listOf(Championnat(nom = "Ligue 1"), Championnat(nom = "Ligue 2"))

            `when`(repository.findAll()).thenReturn(expected)

            val allChampionnats = service.getAll()

            assertThat(allChampionnats).isEqualTo(expected)
        }

        @Test
        fun `doit retourner une liste vide si aucun championnat`() {
            `when`(repository.findAll()).thenReturn(emptyList())

            val allChampionnats = service.getAll()

            assertThat(allChampionnats).isEqualTo(emptyList<Championnat>())
        }

        @Test
        fun `doit retourner le championnat correspondant a un id`() {
            val expected = Championnat(id = 1, nom = "Ligue 1")
            `when`(repository.findById(1)).thenReturn(expected)

            val championnat = service.getById(1)

            assertThat(championnat).isEqualTo(expected)
        }
    }

    @Nested
    inner class CreateFeature {
        @Test
        fun `doit creer un championnat`() {
            val expectedCreatedChampionnat = Championnat(nom = "Bundesliga")

            `when`(repository.saveOrUpdate(expectedCreatedChampionnat)).thenReturn(expectedCreatedChampionnat)

            val championnat = service.createOrEdit(expectedCreatedChampionnat)

            assertThat(championnat).isEqualTo(expectedCreatedChampionnat)
        }
    }

    @Nested
    inner class EditFeature {
        @Test
        fun `doit editer un championnat`() {
            val expectedCreatedChampionnat = Championnat(id = 3, nom = "Bundesliga")

            `when`(repository.saveOrUpdate(expectedCreatedChampionnat)).thenReturn(expectedCreatedChampionnat)

            val championnat = service.createOrEdit(expectedCreatedChampionnat)

            assertThat(championnat).isEqualTo(expectedCreatedChampionnat)
        }
    }

    @Nested
    inner class RemoveFeature {

        @Test
        fun `doit supprimer un championnat correspondant a un id`() {
            val expected = Championnat(id = 1, nom = "Ligue 1")

            `when`(repository.remove(1)).thenReturn(expected)

            val deleteChampionnat = service.delete(1)

            assertThat(deleteChampionnat).isEqualTo(expected)
        }
    }
}
