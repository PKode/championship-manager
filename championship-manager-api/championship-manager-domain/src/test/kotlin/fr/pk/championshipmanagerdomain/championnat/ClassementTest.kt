package fr.pk.championshipmanagerdomain.championnat

import fr.pk.championshipmanagerdomain.equipe.Equipe
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ClassementTest {
    val PSG = Equipe(1, "PSG")
    val OM = Equipe(2, "OM")

    @Test
    fun `doit retourner le classement avec le plus de point`() {
        val classements = listOf(
                Classement(PSG, pts = 10, diff = 5, bp = 10),
                Classement(OM, pts = 1, diff = 5, bp = 10)
        )

        val comparaison = classements.first().compareTo(classements.last())

        assertThat(comparaison).isEqualTo(9)
        assertThat(classements.sortedDescending().map { it.equipe }).containsExactly(PSG, OM)
    }

    @Test
    fun `doit retourner le classement avec la plus grosse difference de buts`() {
        val classements = listOf(
                Classement(OM, pts = 10, diff = 5, bp = 10),
                Classement(PSG, pts = 10, diff = 15, bp = 10)
        )

        val comparaison = classements.first().compareTo(classements.last())

        assertThat(comparaison).isEqualTo(-10)
        assertThat(classements.sortedDescending().map { it.equipe }).containsExactly(PSG, OM)
    }

    @Test
    fun `doit retourner le classement avec le plus de buts pour`() {
        val classements = listOf(
                Classement(OM, pts = 10, diff = 15, bp = 10),
                Classement(PSG, pts = 10, diff = 15, bp = 22)
        )

        val comparaison = classements.first().compareTo(classements.last())

        assertThat(comparaison).isEqualTo(-12)
        assertThat(classements.sortedDescending().map { it.equipe }).containsExactly(PSG, OM)
    }
}
