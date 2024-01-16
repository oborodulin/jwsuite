package com.oborodulin.jwsuite.data.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.DatabaseDao
import com.oborodulin.jwsuite.data.local.db.repositories.sources.LocalDatabaseDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class LocalDatabaseDataSourceImpl @Inject constructor(
    private val databaseDao: DatabaseDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalDatabaseDataSource {
    override fun getDataTables() = databaseDao.findDataTables()

    override suspend fun setCheckpoint() = withContext(dispatcher) { databaseDao.setCheckpoint() }
}
