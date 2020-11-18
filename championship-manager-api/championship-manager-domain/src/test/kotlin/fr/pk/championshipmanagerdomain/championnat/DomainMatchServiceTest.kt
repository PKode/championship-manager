package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.MatchRepository
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.joueur.Joueur
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DomainMatchServiceTest {
    private val repository = mock(MatchRepository::class.java)
    private val service = DomainMatchService(repository)

    private val PSG = Equipe(1, "PSG")
    private val OL = Equipe(2, "OL")
    private val RONALDO = Joueur(nom = "Ronaldo", prenom = "Cristiano", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais")

    @Nested
    inner class CreateFeature {
        @Test
        fun `doit creer un match`() {
            val expectedCreatedMatch = Match(domicile = PSG, exterieur = OL, date = "20/08/2020".toLocalDate().atTime(21, 0))

            `when`(repository.saveOrUpdate(expectedCreatedMatch)).thenReturn(expectedCreatedMatch)

            val match = service.createOrEdit(expectedCreatedMatch)

            Assertions.assertThat(match).isEqualTo(expectedCreatedMatch)
        }
    }

    @Nested
    inner class EditFeature {
        @Test
        fun `doit editer un match`() {
            val expectedCreatedMatch = Match(domicile = PSG, exterieur = OL, date = "20/08/2020".toLocalDate().atTime(21, 0),
                    butDomicile = 2, butExterieur = 1, joueurs = listOf(JoueurStat(RONALDO)))

            `when`(repository.saveOrUpdate(expectedCreatedMatch)).thenReturn(expectedCreatedMatch)

            val match = service.createOrEdit(expectedCreatedMatch)

            Assertions.assertThat(match).isEqualTo(expectedCreatedMatch)
        }
    }

    @Nested
    inner class GetFeature {
        @Test
        fun `doit recuperer tous les matchs d une equipe de la saison en cours`() {
            val expectedCreatedMatchs = listOf(Match(domicile = PSG, exterieur = OL, date = "20/08/2020".toLocalDate().atTime(21, 0),
                    butDomicile = 2, butExterieur = 1, joueurs = listOf(JoueurStat(RONALDO))),
                    Match(domicile = OL, exterieur = PSG, date = "27/08/2020".toLocalDate().atTime(21, 0))
            )

            `when`(repository.findCurrentSaisonByEquipe(PSG.id!!)).thenReturn(2020)
            `when`(repository.findAllByEquipeAndSaison(PSG.id!!, 2020)).thenReturn(expectedCreatedMatchs)

            val matchs = service.getAllMatchsByEquipeForCurrentSaison(PSG.id!!)

            Assertions.assertThat(matchs).usingElementComparatorIgnoringFields("id")
                    .isEqualTo(expectedCreatedMatchs)
        }
    }
}

private fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("d/MM/yyyy"))
}
