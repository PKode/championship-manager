package fr.pk.championshipmanagerinfra

import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdModel
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.io.File

@Configuration
class TestConfiguration {

    @Bean
    @Primary
    fun xodusStore(): TransientEntityStore {
        XdModel.scanJavaClasspath()

        // Initialize Xodus persistent storage
        val xodusStore = StaticStoreContainer.init(
                dbFolder = File("target", "championshipmanager"),
                environmentName = "db"
        )

        // Initialize Xodus-DNQ metadata
        initMetaData(XdModel.hierarchy, xodusStore)
        return xodusStore
    }
}
