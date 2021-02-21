package fr.pk.championshipmanagerapplication.parser

import fr.pk.championshipmanagerapplication.Either
import fr.pk.championshipmanagerdomain.joueur.Joueur
import java.io.InputStream

abstract class FileParser {
    abstract val acceptedExtension: List<String>
    abstract fun parseToJoueur(input: InputStream): List<Either<ApplicationError, Joueur>>

    fun accept(extension: String) = extension in acceptedExtension
}
