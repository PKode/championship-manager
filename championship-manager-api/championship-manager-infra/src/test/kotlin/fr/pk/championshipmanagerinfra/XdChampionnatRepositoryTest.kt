package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import fr.pk.championshipmanagerinfra.repository.XdChampionnatRepository
import jetbrains.exodus.database.TransientEntityStore
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TestConfiguration::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class XdChampionnatRepositoryTest {

    @Autowired
    private lateinit var xdStore: TransientEntityStore

    private lateinit var repository: XdChampionnatRepository

    @BeforeAll
    fun init() {
        repository = XdChampionnatRepository(xdStore)
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
        }
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
