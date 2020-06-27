package fr.pk.championshipmanagerinfra

import jetbrains.exodus.database.TransientEntityStore
import kotlinx.dnq.XdEntityType
import kotlinx.dnq.XdModel
import kotlinx.dnq.store.container.StaticStoreContainer
import kotlinx.dnq.util.initMetaData
import java.io.File

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