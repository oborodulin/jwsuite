package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.DatabaseVersion
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalDatabaseDataSource
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val localDatabaseDataSource: LocalDatabaseDataSource,
    private val databaseVersion: DatabaseVersion
) : DatabaseRepository {
    override fun transferObjectTableNames(transferObjects: List<Pair<TransferObjectType, Boolean>>) =
        flow {
            val tables = mutableMapOf<String, Boolean>()
            transferObjects.sortedBy { it.second }.forEach { transferObject ->
                transferObjectTables[transferObject.first]?.let { names ->
                    names.forEach { tables[it] = transferObject.second }
                }
            }
            emit(tables)
        }

    override fun orderedDataTableNames() = localDatabaseDataSource.getDataTables()

    // API:
    override fun sqliteVersion() = flow { emit(databaseVersion.sqliteVersion) }
    override fun dbVersion() = flow { emit(databaseVersion.dbVersion) }
    override suspend fun checkpoint() = localDatabaseDataSource.setCheckpoint()

    companion object {
        val transferObjectTables =
            mapOf(
                TransferObjectType.ALL to listOf(AppSettingEntity.TABLE_NAME),
                TransferObjectType.MEMBERS to listOf(AppSettingEntity.TABLE_NAME)
            )
    }
}