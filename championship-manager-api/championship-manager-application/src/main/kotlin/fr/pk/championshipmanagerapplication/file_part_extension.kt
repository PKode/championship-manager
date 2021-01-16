package fr.pk.championshipmanagerapplication

import org.springframework.core.io.buffer.DataBuffer
import java.io.File


fun DataBuffer.temp(fileName: String): File {
    val tmpFile = File("uploads/$fileName")
    tmpFile.createNewFile()
    tmpFile.writeBytes(this.asInputStream().readAllBytes())
    return tmpFile
}
