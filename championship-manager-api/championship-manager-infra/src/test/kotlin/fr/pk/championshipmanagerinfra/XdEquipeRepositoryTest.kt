package fr.pk.championshipmanagerinfra

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.repository.XdEquipeRepository
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
internal class XdEquipeRepositoryTest {

    @Autowired
    private lateinit var xdStore: TransientEntityStore

    private lateinit var repository: XdEquipeRepository

    @BeforeAll
    fun init() {
        repository = XdEquipeRepository(xdStore)
        xdStore.transactional {
            XdEquipe.new {
                this.id = 4
                this.nom = "PSG"
            }
            XdEquipe.new {
                this.id = 5
                this.nom = "ASSE"
            }
            XdEquipe.new {
                this.id = 6
                this.nom = "OL"
            }
        }
    }

    @Test
    fun `doit retourner toutes les equipes en base`() {

        val equipes = repository.findAll()

        assertThat(equipes).containsExactlyInAnyOrderElementsOf(equipes())
    }

    @Test
    fun `doit creer une equipe, la modifier et la supprimer`() {

        // Create
        val new = Equipe(nom = "CFA")
        val equipe = repository.saveOrUpdate(new)

        assertThat(equipe.nom).isEqualTo(new.nom)
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
                    Equipe(4, "PSG"),
                    Equipe(5, "ASSE"),
                    Equipe(6, "OL")
            )
}
