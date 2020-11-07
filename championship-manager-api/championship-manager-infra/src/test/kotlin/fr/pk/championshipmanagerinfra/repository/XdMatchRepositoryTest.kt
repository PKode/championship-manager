package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.JoueurStat
import fr.pk.championshipmanagerdomain.championnat.Match
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
}
