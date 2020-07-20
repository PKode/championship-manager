package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.championnat.port.MatchRepository
import fr.pk.championshipmanagerdomain.equipe.Equipe
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DomainMatchServiceTest {
    private val repository = Mockito.mock(MatchRepository::class.java)
    private val service = DomainMatchService(repository)

    private val PSG = Equipe(1, "PSG")
    private val OL = Equipe(2, "OL")

    @Nested
    inner class CreateFeature {
        @Test
        fun `doit creer un championnat`() {
            val expectedCreatedMatch = Match(domicile = PSG, exterieur = OL, date = "20/08/2020".toLocalDate().atTime(21, 0))

            Mockito.`when`(repository.saveOrUpdate(expectedCreatedMatch)).thenReturn(expectedCreatedMatch)

            val match = service.createOrEdit(expectedCreatedMatch)

            Assertions.assertThat(match).isEqualTo(expectedCreatedMatch)
        }
    }

    @Nested
    inner class EditFeature {
        @Test
        fun `doit editer un championnat`() {
            val expectedCreatedMatch = Match(domicile = PSG, exterieur = OL, date = "20/08/2020".toLocalDate().atTime(21, 0),
                    butDomicile = 2, butExterieur = 1)

            Mockito.`when`(repository.saveOrUpdate(expectedCreatedMatch)).thenReturn(expectedCreatedMatch)

            val match = service.createOrEdit(expectedCreatedMatch)

            Assertions.assertThat(match).isEqualTo(expectedCreatedMatch)
        }
    }
}

private fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("d/MM/yyyy"))
}
