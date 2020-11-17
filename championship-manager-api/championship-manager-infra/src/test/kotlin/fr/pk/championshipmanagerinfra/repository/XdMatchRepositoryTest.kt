package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.*
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.joueur.Joueur
import fr.pk.championshipmanagerinfra.TestConfiguration
import fr.pk.championshipmanagerinfra.entities.*
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.time.LocalDate

@SpringBootTest(classes = [TestConfiguration::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class XdMatchRepositoryTest : XdRepositoryTest() {
    private val xdStore = xodusStore(XdMatch, XdEquipe, XdChampionnat, XdJoueurStat, XdJoueur)

    private val repository = XdMatchRepository(xdStore)
    private val championnatRepository = XdChampionnatRepository(xdStore)

    private val PSG = Equipe(1, "PSG")
    private val OL = Equipe(2, "OL")
    private val RONALDO = Joueur(7, nom = "Ronaldo", prenom = "Cristiano", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais")

    @BeforeAll
    fun init() {
        xdStore.transactional {
            XdEquipe.new {
                this.id = PSG.id!!
                this.nom = PSG.nom
            }

            XdEquipe.new {
                this.id = OL.id!!
                this.nom = OL.nom
            }

            XdJoueur.new {
                this.id = 7
                this.nom = "Ronaldo"
                this.prenom = "Cristiano"
                this.poste = "ATT"
                this.nationalite = "Portugais"
                this.dateNaissance = DateTime("05/02/1985".toLocalDate().toEpochDay())
                this.taille = 187
                this.poids = 84
            }
        }
    }

    //TODO: move up in abstract
    @AfterAll
    fun clean() {
        File(xdStore.persistentStore.location).deleteRecursively()
        println("Exodus removing environment")
    }

    // TODO: add test to ensure that joueur are not added if already exist in match
    @Test
    fun `doit creer un match et mettre a jour le score d un match`() {
        val dateMatch = "30/04/2020".toLocalDate().atTime(20, 0)
        val match = Match(domicile = PSG, exterieur = OL, date = dateMatch)

        val createdMatch = repository.saveOrUpdate(match)

        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(createdMatch).isEqualToIgnoringGivenFields(match, "id")

        val matchWithScore = match.copy(id = createdMatch.id, butDomicile = 3, butExterieur = 0, joueurs = listOf(JoueurStat(RONALDO, 3)))

        val updatedMatch = repository.saveOrUpdate(matchWithScore)
        assertThat(repository.findById(updatedMatch.id!!))
                .isEqualTo(matchWithScore)
                .isEqualTo(updatedMatch)
    }

    @Test
    fun `doit recuperer le dernier match joue d une equipe`() {
        val expectedLastMatchPlayed = Match(null, PSG, OL, 3, 1)
        val matchs = listOf(
                Match(null, PSG, OL, 1, 0),
                Match(null, OL, PSG, 1, 2),
                expectedLastMatchPlayed,
                Match(null, OL, PSG)
        )

        matchs.forEach { repository.saveOrUpdate(it) }

        val lastMatchPlayed = repository.findLastPlayedMatchByEquipe(PSG.id!!)

        assertThat(lastMatchPlayed).isEqualToIgnoringGivenFields(expectedLastMatchPlayed, "id")
    }

    @Test
    fun `doit recuperer tous les matchs d une equipe pour une saison`() {
        val matchs2020 = listOf(
                Match(null, PSG, OL, 1, 0),
                Match(null, OL, PSG, 1, 2),
                Match(null, PSG, OL, 3, 1)
        )
        val matchs2021 = listOf(Match(null, OL, PSG))
        val saison2020 = Saison(2020, listOf(Journee(1, matchs2020)))
        val saison2021 = Saison(2021, listOf(Journee(1, matchs2021)))

        val (championnatId) = championnatRepository.saveOrUpdate(Championnat(null, "Ligue 1"))
        championnatRepository.saveNewSaison(championnatId!!, saison2020)
        championnatRepository.saveNewSaison(championnatId, saison2021)

        val findAllByEquipeAndSaison = repository.findAllByEquipeAndSaison(PSG.id!!, 2021)

        assertThat(findAllByEquipeAndSaison)
                .usingElementComparatorIgnoringFields("id")
                .isEqualTo(saison2021.journees.flatMap { it.matchs })
    }
}
