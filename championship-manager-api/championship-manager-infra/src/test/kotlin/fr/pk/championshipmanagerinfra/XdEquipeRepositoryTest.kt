package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdMatch
import fr.pk.championshipmanagerinfra.repository.XdEquipeRepository
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
internal class XdEquipeRepositoryTest : XdRepositoryTest() {

    private val xdStore = xodusStore(XdChampionnat, XdEquipe, XdMatch)

    private val repository = XdEquipeRepository(xdStore)

    private val championnatId = 1

    @BeforeAll
    fun init() {
        xdStore.transactional {
            val championnat = XdChampionnat.new {
                this.id = championnatId
                this.nom = "Ligue 1"
            }
            XdEquipe.new {
                this.id = 4
                this.nom = "PSG"
                this.championnat = championnat
            }
            XdEquipe.new {
                this.id = 5
                this.nom = "ASSE"
                this.championnat = championnat
            }
            XdEquipe.new {
                this.id = 6
                this.nom = "OL"
            }
        }
    }

    @AfterAll
    fun clean() {
        File(xdStore.persistentStore.location).deleteRecursively()
        println("Exodus removing environment")
    }

    @Test
    fun `doit retourner toutes les equipes en base`() {

        val equipes = repository.findAll()

        assertThat(equipes).containsExactlyInAnyOrderElementsOf(equipes())
    }

    @Test
    fun `doit retourner toutes les equipes en base pour un championnat`() {

        val equipes = repository.findAllEquipeByChampionnat(championnatId)

        assertThat(equipes).containsExactlyInAnyOrderElementsOf(listOf(
                Equipe(4, "PSG", championnat = Championnat(championnatId, "Ligue 1")),
                Equipe(5, "ASSE", championnat = Championnat(championnatId, "Ligue 1")))
        )
    }

    @Test
    fun `doit creer une equipe, la modifier et la supprimer`() {

        // Create
        val new = Equipe(nom = "Lens", championnat = Championnat(id = championnatId, nom = "Ligue 1"))
        val equipe = repository.saveOrUpdate(new)

        assertThat(equipe.nom).isEqualTo(new.nom)
        assertThat(equipe.championnat?.nom).isEqualTo("Ligue 1")
        assertThat(repository.findAll().size).isEqualTo(4)

        // Update and get
        val updated = repository.saveOrUpdate(Equipe(equipe.id, "Nice"))
        assertThat(repository.findById(equipe.id!!).nom).isEqualTo("Nice")

        // Remove
        val removed = repository.remove(equipe.id!!)

        assertThat(removed).isEqualTo(updated)
        assertThat(repository.findAll().size).isEqualTo(3)
    }

    @Test
    fun `doit renvoyer une exception si aucune equipe trouve`() {

        assertThatThrownBy { repository.findById(99) }
                .isInstanceOf(NoSuchElementException::class.java)
                .hasMessageContaining("Aucun XdEquipe matche la condition :: PropertyEqual(id=99)")

        assertThatThrownBy { repository.remove(99) }
                .isInstanceOf(NoSuchElementException::class.java)
                .hasMessageContaining("Aucun XdEquipe matche la condition :: PropertyEqual(id=99)")

    }

    fun equipes(): List<Equipe> =
            listOf(
                    Equipe(4, "PSG", championnat = Championnat(championnatId, "Ligue 1")),
                    Equipe(5, "ASSE", championnat = Championnat(championnatId, "Ligue 1")),
                    Equipe(6, "OL")
            )
}
