package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.joueur.Joueur
import fr.pk.championshipmanagerinfra.TestConfiguration
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdJoueur
import fr.pk.championshipmanagerinfra.entities.XdMatch
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
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
internal class XdJoueurRepositoryTest : XdRepositoryTest() {

    private val xdStore = xodusStore(XdJoueur, XdEquipe, XdChampionnat, XdMatch)

    private val repository = XdJoueurRepository(xdStore)

    @BeforeAll
    fun init() {
        xdStore.transactional {
            val CACV = XdEquipe.new {
                this.id = 1
                this.nom = "CACV"
            }

            XdJoueur.new {
                this.id = 10
                this.nom = "Kerirzin"
                this.prenom = "Pierrick"
                this.poste = "MO"
                this.nationalite = "Français"
                this.dateNaissance = DateTime("30/04/1993".toLocalDate().toEpochDay())
                this.taille = 180
                this.poids = 75
                this.equipe = CACV
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

    @AfterAll
    fun clean() {
        File(xdStore.persistentStore.location).deleteRecursively()
        println("Exodus removing environment")
    }


    @Test
    fun `doit retourner tous les joueurs en base`() {

        val joueurs = repository.findAll()

        assertThat(joueurs).containsExactlyInAnyOrderElementsOf(joueurs())
    }

    @Test
    fun `doit creer un joueur, le modifier et le supprimer`() {

        // Create
        val new = Joueur(nom = "Zidane", prenom = "Zinedine", dateNaissance = LocalDate.of(1972, 6, 23),
                poste = "MO", taille = 185, poids = 80, nationalite = "Français", equipe = Equipe(1, "Real Madrid"))

        val joueur = repository.saveOrUpdate(new)

        assertThat(joueur.nom).isEqualTo(new.nom)
        assertThat(repository.findAll().size).isEqualTo(3)

        // Update and get
        val updated = repository.saveOrUpdate(new.copy(id = joueur.id, poids = 77))
        assertThat(repository.findById(joueur.id!!).poids).isEqualTo(77)

        // Remove
        val removed = repository.remove(joueur.id!!)

        assertThat(removed).isEqualTo(updated)
        assertThat(repository.findAll().size).isEqualTo(2)
    }

    @Test
    fun `doit renvoyer une exception si aucun joueur trouve`() {

        assertThatThrownBy { repository.findById(99) }
                .isInstanceOf(NoSuchElementException::class.java)
                .hasMessageContaining("Aucun XdJoueur matche la condition :: PropertyEqual(id=99)")

        assertThatThrownBy { repository.remove(99) }
                .isInstanceOf(NoSuchElementException::class.java)
                .hasMessageContaining("Aucun XdJoueur matche la condition :: PropertyEqual(id=99)")

    }

    fun joueurs(): List<Joueur> =
            listOf(
                    Joueur(id = 7, nom = "Ronaldo", prenom = "Cristiano",
                            dateNaissance = LocalDate.of(1985, 2, 5),
                            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais"),
                    Joueur(id = 10, nom = "Kerirzin", prenom = "Pierrick",
                            dateNaissance = LocalDate.of(1993, 4, 30),
                            poste = "MO", taille = 180, poids = 75, nationalite = "Français",
                            equipe = Equipe(1, "CACV"))
            )
}
