package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.championnat.Journee
import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.championnat.Saison
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerinfra.TestConfiguration
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdMatch
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest(classes = [TestConfiguration::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class XdChampionnatRepositoryTest : XdRepositoryTest() {

    private val xdStore = xodusStore(XdChampionnat, XdMatch, XdEquipe)

    private val repository = XdChampionnatRepository(xdStore)

    private val PSG = Equipe(1, "PSG")
    private val OL = Equipe(2, "OL")

    @BeforeAll
    fun init() {
        xdStore.transactional {
            XdChampionnat.new {
                this.id = 1
                this.nom = "Ligue 1"
            }
            XdChampionnat.new {
                this.id = 2
                this.nom = "Ligue 2"
            }
            XdChampionnat.new {
                this.id = 3
                this.nom = "National"
            }

            XdEquipe.new {
                this.id = PSG.id!!
                this.nom = PSG.nom
            }

            XdEquipe.new {
                this.id = OL.id!!
                this.nom = OL.nom
            }
        }
    }

    @AfterAll
    fun clean() {
        File(xdStore.persistentStore.location).deleteRecursively()
        println("Exodus removing environment")
    }


    @Test
    fun `doit retourner tous les championnats en base`() {

        val championnats = repository.findAll()

        assertThat(championnats).containsExactlyInAnyOrderElementsOf(championnats())
    }

    @Test
    fun `doit creer un championnat, le modifier et le supprimer`() {

        // Create
        val new = Championnat(nom = "CFA")
        val championnat = repository.saveOrUpdate(new)

        assertThat(championnat.nom).isEqualTo(new.nom)
        assertThat(repository.findAll().size).isEqualTo(4)

        // Update and get
        val updated = repository.saveOrUpdate(Championnat(championnat.id, "CFA 2"))
        assertThat(repository.findById(championnat.id!!).nom).isEqualTo("CFA 2")

        // Remove
        val removed = repository.remove(championnat.id!!)

        assertThat(removed).isEqualTo(updated)
        assertThat(repository.findAll().size).isEqualTo(3)
    }

    @Test
    fun `doit ajouter une saison a un championnat et retourner la liste des matchs de cette saison`() {
        val saison = saison(2020)
        val championnat = repository.saveNewSaison(1, saison)

        assertThat(championnat.nom).isEqualTo("Ligue 1")
        assertThat(championnat.saisons).isEqualTo(listOf(saison(2020)))

        val matchs = repository.findMatchsBySaisonAndChampionnat(1, 2020)

        assertThat(matchs).size().isEqualTo(2)
        assertThat(matchs).containsExactly(Match(PSG, OL), Match(OL, PSG))
    }

    private fun saison(annee: Int): Saison {
        return Saison(annee, listOf(
                Journee(1, listOf(
                        Match(PSG, OL),
                        Match(OL, PSG)
                ))
        ))
    }

    @Test
    fun `doit renvoyer une exception si aucun championnat trouve`() {

        assertThatThrownBy { repository.findById(99) }
                .isInstanceOf(NoSuchElementException::class.java)
                .hasMessageContaining("Aucun XdChampionnat matche la condition :: PropertyEqual(id=99)")

        assertThatThrownBy { repository.remove(99) }
                .isInstanceOf(NoSuchElementException::class.java)
                .hasMessageContaining("Aucun XdChampionnat matche la condition :: PropertyEqual(id=99)")

    }

    fun championnats(): List<Championnat> =
            listOf(
                    Championnat(1, "Ligue 1"),
                    Championnat(2, "Ligue 2"),
                    Championnat(3, "National")
            )
}
