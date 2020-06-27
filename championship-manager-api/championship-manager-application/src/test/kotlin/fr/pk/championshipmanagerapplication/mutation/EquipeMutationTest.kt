package fr.pk.championshipmanagerapplication.mutation

import fr.pk.championshipmanagerapplication.dto.ChampionnatDto
import fr.pk.championshipmanagerapplication.dto.EquipeDto
import fr.pk.championshipmanagerdomain.championnat.Championnat
import fr.pk.championshipmanagerdomain.equipe.Equipe
import fr.pk.championshipmanagerdomain.equipe.port.EquipeService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.verify

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EquipeMutationTest {

    private val equipeService = Mockito.mock(EquipeService::class.java)
    private val mutation = EquipeMutation(equipeService)

    @Nested
    inner class CreateMutation {

        @Test
        fun `doit creer une nouvelle equipe`() {

            val nom = "PSG"

            val equipeToCreate = Equipe(nom = nom, championnat = Championnat(1, "Ligue 1"))
            Mockito.`when`(equipeService.createOrEdit(equipeToCreate)).thenReturn(Equipe(1, nom, Championnat(1, "Ligue 1")))

            val championnatDto = ChampionnatDto(1, "Ligue 1")
            val equipe = mutation.equipe(equipe = EquipeDto(nom = nom, championnat = championnatDto))

            Assertions.assertThat(equipe).isEqualTo(EquipeDto(1, nom, championnatDto))
            verify(equipeService, Mockito.times(1)).createOrEdit(equipeToCreate)
        }
    }

    @Nested
    inner class EditMutation {

        @Test
        fun `doit modifier une equipe`() {

            val nom = "PSG"

            val equipeToCreate = Equipe(id = 3, nom = nom)
            Mockito.`when`(equipeService.createOrEdit(equipeToCreate)).thenReturn(Equipe(3, nom))

            val equipe = mutation.equipe(EquipeDto(id = 3, nom = nom))

            Assertions.assertThat(equipe).isEqualTo(EquipeDto(3, nom))
            verify(equipeService, Mockito.times(1)).createOrEdit(equipeToCreate)
        }
    }

    @Nested
    inner class RemoveMutation {

        @Test
        fun `doit supprimer une equipe`() {

            val id = 1
            val nom = "Marseille"

            Mockito.`when`(equipeService.delete(id)).thenReturn(Equipe(id, nom))

            val equipe = mutation.deleteEquipe(id)

            Assertions.assertThat(equipe).isEqualTo(EquipeDto(1, nom))
            verify(equipeService, Mockito.times(1)).delete(id)
        }
    }
}
