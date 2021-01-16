package fr.pk.championshipmanagerapplication.parser

import fr.pk.championshipmanagerdomain.joueur.Joueur
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class ExcelFileParser : FileParser() {
    override val acceptedExtension = listOf("xls", "xlsx")

    override fun parseToJoueur(input: InputStream): List<Joueur> {
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

private fun Row.toJoueur(): Joueur {
    val nomPrenom = this.getCell(2).stringCellValue.split(" ")
    return Joueur(
            nom = nomPrenom[0].toLowerCase().capitalize(),
            prenom = nomPrenom.getOrNull(1)?.toLowerCase()?.capitalize(),
            poste = this.getCell(1).stringCellValue,
            dateNaissance = this.getCell(3).localDateTimeCellValue.toLocalDate(),
            taille = this.getCell(4).stringCellValue.replace("m", "").toInt(),
            poids = this.getCell(5).stringCellValue.substringBefore(" ").toInt(),
            nationalite = this.getCell(11).stringCellValue.toLowerCase().capitalize()
    )
}
