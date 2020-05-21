package fr.pk.championshipmanagerapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["fr.pk.*"])
class ChampionshipManagerAppApplication


fun main(args: Array<String>) {
    runApplication<ChampionshipManagerAppApplication>(*args)
}


