package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.championnat.Championnat
import jetbrains.exodus.database.TransientEntityStore
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
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

    @AfterAll
    fun end() {
        xdStore.persistentStore.clear()
    }

    @Test
    fun `doit retourner tous les championnats en base`() {

        val championnats = repository.findAll()

        Assertions.assertThat(championnats).containsExactlyInAnyOrderElementsOf(championnats())
    }

    @Test
    fun `doit creer un championnat, le modifier et le supprimer`() {

        // Create
        val new = Championnat(nom = "CFA")
        val championnat = repository.saveOrUpdate(new)

        Assertions.assertThat(championnat.nom).isEqualTo(new.nom)
        Assertions.assertThat(repository.findAll().size).isEqualTo(4)

        // Update and get
        val updated = repository.saveOrUpdate(Championnat(championnat.id, "CFA 2"))
        Assertions.assertThat(repository.findById(championnat.id!!).nom).isEqualTo("CFA 2")

        // Remove
        val removed = repository.remove(championnat.id!!)

        Assertions.assertThat(removed).isEqualTo(updated)
        Assertions.assertThat(repository.findAll().size).isEqualTo(3)
    }

    fun championnats(): List<Championnat> =
            listOf(
                    Championnat(1, "Ligue 1"),
                    Championnat(2, "Ligue 2"),
                    Championnat(3, "National")
            )
}
