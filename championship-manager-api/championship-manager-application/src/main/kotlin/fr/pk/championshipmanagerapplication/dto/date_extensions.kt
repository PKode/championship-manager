package fr.pk.championshipmanagerapplication.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.toFrDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/YYYY H:mm"))
}

fun LocalDate.toFrDateString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"))
}

fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
