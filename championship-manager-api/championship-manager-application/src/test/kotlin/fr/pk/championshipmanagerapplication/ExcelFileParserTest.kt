package fr.pk.championshipmanagerapplication

import fr.pk.championshipmanagerapplication.parser.ExcelFileParser
import fr.pk.championshipmanagerdomain.joueur.Joueur
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate

internal class ExcelFileParserTest {

    private val parser = ExcelFileParser()

    @Test
    fun `doit mapper un file part en une liste de championnat`() {
        val expected = listOf(
                Joueur(null, "Abardonado", "Jacques", "D", "France", LocalDate.of(1978, 5, 27), 183, 76),
                Joueur(null, "Abdessadki", "Yacine", "M", "France", LocalDate.of(1981, 1, 1), 178, 71),
                Joueur(null, "Abidal", "Eric", "D", "France", LocalDate.of(1979, 9, 11), 187, 80)
        )
        val file = File("src/test/resources/Pros.xls")

        val extractChampionnat = parser.parseToJoueur(file.inputStream())

        Assertions.assertThat(extractChampionnat).usingRecursiveComparison()
                .isEqualTo(expected)
    }
}
