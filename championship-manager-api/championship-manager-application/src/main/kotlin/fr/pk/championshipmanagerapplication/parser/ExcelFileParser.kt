package fr.pk.championshipmanagerapplication.parser

import fr.pk.championshipmanagerapplication.Either
import fr.pk.championshipmanagerapplication.left
import fr.pk.championshipmanagerapplication.parser.ApplicationError.ParsingFileError
import fr.pk.championshipmanagerapplication.right
import fr.pk.championshipmanagerdomain.joueur.Joueur
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class ExcelFileParser : FileParser() {
    override val acceptedExtension = listOf("xls", "xlsx")

    override fun parseToJoueur(input: InputStream): List<Either<ApplicationError, Joueur>> {
        return WorkbookFactory.create(input)
                .getSheetAt(0)
                .rowIterator()
                .skipHeader()
                .asSequence()
                .takeWhile { it.getCell(0) != null }
                .map { it.toJoueur() }
                .toList()
    }
}

private fun MutableIterator<Row>.skipHeader(): MutableIterator<Row> = this.apply { this.next() }

private fun Row.toJoueur(): Either<ApplicationError, Joueur> {
    return try {
        val nomPrenom = this.getCell(2).stringCellValue.split(" ")
        Joueur(
                nom = nomPrenom[0].toLowerCase().capitalize(),
                prenom = nomPrenom.getOrNull(1)?.toLowerCase()?.capitalize(),
                poste = this.getCell(1).stringCellValue,
                dateNaissance = this.getCell(3).localDateTimeCellValue.toLocalDate(),
                taille = this.getCell(4).stringCellValue.replace("m", "").toInt(),
                poids = this.getCell(5).stringCellValue.substringBefore(" ").toInt(),
                nationalite = this.getCell(11).stringCellValue.toLowerCase().capitalize()
        ).right()
    } catch (exception: IllegalStateException) {
        ParsingFileError("Impossible de lire la Ligne ${this.rowNum}").left()
    } catch (exception: NullPointerException) {
        ParsingFileError("Valeur manquante à la ligne ${this.rowNum}").left()
    }
}

sealed class ApplicationError(val message: String) {
    class ParsingFileError(message: String) : ApplicationError(message)
}
