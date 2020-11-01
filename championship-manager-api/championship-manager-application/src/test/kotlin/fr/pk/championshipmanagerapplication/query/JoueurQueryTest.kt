package fr.pk.championshipmanagerapplication.query

import fr.pk.championshipmanagerapplication.dto.JoueurDto
import fr.pk.championshipmanagerdomain.joueur.Joueur
import fr.pk.championshipmanagerdomain.joueur.port.JoueurService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import java.time.LocalDate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class JoueurQueryTest {
    private val joueurService = mock(JoueurService::class.java)
    private val query = JoueurQuery(joueurService)


    private val RONALDO = Joueur(nom = "Ronaldo", prenom = "Cristiano", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais")

    private val MESSI = Joueur(nom = "Messi", prenom = "Leo", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 167, poids = 64, nationalite = "Argentin")

    @Nested
    inner class GetQuery {

        @Test
        fun `doit retourner la liste des joueurs`() {
            // Given
            val domainList = listOf(RONALDO, MESSI)

            // When
            `when`(joueurService.getAll()).thenReturn(domainList)

            val joueurs = query.joueurs()

            // Then
            val dtoList = listOf(JoueurDto(RONALDO), JoueurDto(MESSI))

            assertThat(joueurs).isEqualTo(dtoList)
            verify(joueurService, times(1)).getAll()
        }

        @Test
        fun `doit retourner le joueur correspondant a id`() {
            // Given
            val domainJoueur = RONALDO

            // When
            `when`(joueurService.getById(1)).thenReturn(RONALDO)

            val joueur = query.joueur(1)

            // Then
            val expectedDto = JoueurDto(RONALDO)

            assertThat(joueur).isEqualTo(expectedDto)
            verify(joueurService, times(1)).getById(1)
        }

        @Test
        fun `doit retourner le joueur d une equipe`() {
            // Given
            val domainJoueur = RONALDO

            // When
            `when`(joueurService.getJoueursByEquipe(1)).thenReturn(listOf(RONALDO))

            val joueur = query.joueursByEquipe(1)

            // Then
            val expectedDto = listOf(JoueurDto(RONALDO))

            assertThat(joueur).containsAll(expectedDto)
            verify(joueurService, times(1)).getJoueursByEquipe(1)
        }
    }
}
