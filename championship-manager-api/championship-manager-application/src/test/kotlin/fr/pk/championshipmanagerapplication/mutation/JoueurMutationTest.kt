package fr.pk.championshipmanagerapplication.mutation

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
internal class JoueurMutationTest {

    private val joueurService = mock(JoueurService::class.java)
    private val mutation = JoueurMutation(joueurService)

    private val RONALDO = Joueur(nom = "Ronaldo", prenom = "Cristiano", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais")

    private val RONALDO_WITH_ID = RONALDO.copy(id = 1)

    @Nested
    inner class CreateMutation {

        @Test
        fun `doit creer un nouveau joueur`() {

            `when`(joueurService.createOrEdit(RONALDO)).thenReturn(RONALDO_WITH_ID)

            val joueur = mutation.joueur(JoueurDto(RONALDO))

            assertThat(joueur).isEqualTo(JoueurDto(RONALDO_WITH_ID))
            verify(joueurService, times(1)).createOrEdit(RONALDO)
        }
    }

    @Nested
    inner class EditMutation {

        @Test
        fun `doit modifier un joueur`() {

            val joueurToCreate = RONALDO_WITH_ID
            `when`(joueurService.createOrEdit(joueurToCreate)).thenReturn(RONALDO_WITH_ID)

            val joueur = mutation.joueur(JoueurDto(RONALDO_WITH_ID))

            assertThat(joueur).isEqualTo(JoueurDto(RONALDO_WITH_ID))
            verify(joueurService, times(1)).createOrEdit(joueurToCreate)
        }
    }

    @Nested
    inner class RemoveMutation {

        @Test
        fun `doit supprimer un joueur`() {

            val id = RONALDO_WITH_ID.id!!
            `when`(joueurService.delete(id)).thenReturn(RONALDO_WITH_ID)

            val joueur = mutation.deleteJoueur(id)

            assertThat(joueur).isEqualTo(JoueurDto(RONALDO_WITH_ID))
            verify(joueurService, times(1)).delete(id)
        }
    }
}
