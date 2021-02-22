package fr.pk.championshipmanagerapplication.controller

import fr.pk.championshipmanagerapplication.Either
import fr.pk.championshipmanagerapplication.dto.JoueurDto
import fr.pk.championshipmanagerapplication.flatMap
import fr.pk.championshipmanagerapplication.parser.ApplicationError
import fr.pk.championshipmanagerapplication.parser.FileParser
import fr.pk.championshipmanagerapplication.right
import fr.pk.championshipmanagerdomain.joueur.port.JoueurService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@RestController
class JoueurController(private val joueurService: JoueurService,
                       private val filesParser: List<FileParser>) {

    @PostMapping(value = ["/upload/joueur"],
            consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(value = HttpStatus.OK)
    fun upload(@RequestPart("file") file: FilePart): Mono<List<Either<ApplicationError, JoueurDto>>> {
        return file.content()
                .map { data ->
                    filesParser[file.filename()].parseToJoueur(data.asInputStream())
                            .flatMap { joueur -> JoueurDto(joueurService.createOrEdit(joueur)).right() }
                }.toMono()
    }
}

operator fun List<FileParser>.get(fileName: String): FileParser = this.firstOrNull { it.accept(fileName.substringAfterLast(".")) }
        ?: error("No file parser configure for file $fileName")
