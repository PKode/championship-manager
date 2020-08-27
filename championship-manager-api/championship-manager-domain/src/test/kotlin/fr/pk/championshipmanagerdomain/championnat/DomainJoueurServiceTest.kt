package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.joueur.DomainJoueurService
import fr.pk.championshipmanagerdomain.joueur.Joueur
import fr.pk.championshipmanagerdomain.joueur.port.JoueurRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DomainJoueurServiceTest {

    private val repository = mock(JoueurRepository::class.java)
    private val service = DomainJoueurService(repository)

    private val RONALDO = Joueur(nom = "Ronaldo", prenom = "Cristiano", dateNaissance = LocalDate.of(1985, 2, 5),
            poste = "ATT", taille = 187, poids = 84, nationalite = "Portugais")

    @Nested
    inner class GetFeature {
        @Test
        fun `doit retourner tous les joueurs`() {
            val expected = listOf(
                    RONALDO,
                    Joueur(nom = "M'Bappé", prenom = "Kylian", dateNaissance = LocalDate.of(1998, 12, 20),
                            poste = "ATT", taille = 178, poids = 74, nationalite = "Français")
            )

            `when`(repository.findAll()).thenReturn(expected)

            val allJoueurs = service.getAll()

            assertThat(allJoueurs).isEqualTo(expected)
        }

        @Test
        fun `doit retourner une liste vide si aucun joueur`() {
            `when`(repository.findAll()).thenReturn(emptyList())

            val allJoueurs = service.getAll()

            assertThat(allJoueurs).isEqualTo(emptyList<Joueur>())
        }

        @Test
        fun `doit retourner le joueur correspondant a un id`() {
            val expected = RONALDO.copy(id = 7)

            `when`(repository.findById(7)).thenReturn(expected)

            val joueur = service.getById(7)

            assertThat(joueur).isEqualTo(expected)
        }
    }

    @Nested
    inner class CreateFeature {
        @Test
        fun `doit creer un joueur`() {
            val expectedCreatedJoueur = RONALDO

            `when`(repository.saveOrUpdate(expectedCreatedJoueur)).thenReturn(expectedCreatedJoueur)

            val joueur = service.createOrEdit(expectedCreatedJoueur)

            assertThat(joueur).isEqualTo(expectedCreatedJoueur)
        }
    }

    @Nested
    inner class EditFeature {
        @Test
        fun `doit editer un joueur`() {
            val expectedCreatedJoueur = RONALDO.copy(id = 7)

            `when`(repository.saveOrUpdate(expectedCreatedJoueur)).thenReturn(expectedCreatedJoueur)

            val joueur = service.createOrEdit(expectedCreatedJoueur)

            assertThat(joueur).isEqualTo(expectedCreatedJoueur)
        }
    }

    @Nested
    inner class RemoveFeature {

        @Test
        fun `doit supprimer un joueur correspondant a un id`() {
            val expected = RONALDO.copy(id = 7)

            `when`(repository.remove(7)).thenReturn(expected)

            val deleteJoueur = service.delete(7)

            assertThat(deleteJoueur).isEqualTo(expected)
        }
    }
}
