package fr.pk.championshipmanagerinfra.repository

import fr.pk.championshipmanagerdomain.championnat.Match
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerinfra.TestConfiguration
import fr.pk.championshipmanagerinfra.entities.XdChampionnat
import fr.pk.championshipmanagerinfra.entities.XdEquipe
import fr.pk.championshipmanagerinfra.entities.XdMatch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest(classes = [TestConfiguration::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class XdMatchRepositoryTest : XdRepositoryTest() {
    private val xdStore = xodusStore(XdMatch, XdEquipe, XdChampionnat)

    private val repository = XdMatchRepository(xdStore)

    private val PSG = Equipe(1, "PSG")
    private val OL = Equipe(2, "OL")

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
        }
    }

    //TODO: move up in abstract
    @AfterAll
    fun clean() {
        File(xdStore.persistentStore.location).deleteRecursively()
        println("Exodus removing environment")
    }

    @Test
    fun `doit creer un match et mettre a jour le score d un match`() {
        val dateMatch = "30/04/2020".toLocalDate().atTime(20, 0)
        val match = Match(domicile = PSG, exterieur = OL, date = dateMatch)

        val createdMatch = repository.saveOrUpdate(match)

        assertThat(repository.findAll().size).isEqualTo(1)
        assertThat(createdMatch).isEqualTo(match)

        val matchWithScore = match.copy(butDomicile = 3, butExterieur = 0)

        val updatedMatch = repository.saveOrUpdate(matchWithScore)
        assertThat(repository.findByEquipesIdsAndDate(PSG.id!!, OL.id!!, dateMatch)).isEqualTo(matchWithScore).isEqualTo(updatedMatch)
    }
}
