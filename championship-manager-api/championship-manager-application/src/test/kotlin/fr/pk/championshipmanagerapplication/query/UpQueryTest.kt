package fr.pk.championshipmanagerapplication.query

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class UpQueryTest {
    private val query = UpQuery()

    @Test
    fun `should return up string`() {
        val up = query.up()
        Assertions.assertThat(up).isEqualTo("UP")
    }

}
