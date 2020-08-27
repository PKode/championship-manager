package fr.pk.championshipmanagerinfra.repository

import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdEntityType
import kotlinx.dnq.XdModel
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class XdRepositoryTest {

    fun xodusStore(vararg entities: XdEntityType<*>): TransientEntityStore {

        XdModel.registerNodes(*entities)

        // Initialize Xodus persistent storage
        val xodusStore = StaticStoreContainer.init(
                dbFolder = File("target", javaClass.simpleName),
                environmentName = "db"
        )

        // Initialize Xodus-DNQ metadata
        initMetaData(XdModel.hierarchy, xodusStore)
        return xodusStore
    }
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("d/MM/yyyy"))
}
