package com.oborodulin.jwsuite.data_congregation.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalCongregationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalCongregationDataSourceImpl @Inject constructor(
    private val congregationDao: CongregationDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalCongregationDataSource {
    override fun getCongregations() = congregationDao.findDistinctAll()

    override fun getCongregation(congregationId: UUID) =
        congregationDao.findDistinctById(congregationId)

    override fun getFavoriteCongregation() = congregationDao.findDistinctFavorite()
    override fun getFavoriteCongregationTotals() = congregationDao.findDistinctTotals()
    override suspend fun insertCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.insertWithFavoriteAndTotals(congregation)
        }

    override suspend fun updateCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.updateWithFavoriteAndTotals(congregation)
        }

    override suspend fun deleteCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.delete(congregation)
        }

    override suspend fun deleteCongregationById(congregationId: UUID) = withContext(dispatcher) {
        congregationDao.deleteById(congregationId)
    }

    override suspend fun deleteCongregations(congregations: List<CongregationEntity>) =
        withContext(dispatcher) {
            congregationDao.delete(congregations)
        }

    override suspend fun deleteAllCongregations() = withContext(dispatcher) {
        congregationDao.deleteAll()
    }

    override suspend fun makeFavoriteCongregationById(congregationId: UUID) =
        withContext(dispatcher) {
            congregationDao.makeFavoriteById(congregationId)
        }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getCongregationEntities(username: String?, byFavorite: Boolean) =
        congregationDao.selectEntities(username, byFavorite)

    override fun getCongregationTotalEntities(username: String?, byFavorite: Boolean) =
        congregationDao.selectTotalEntities(username, byFavorite)

    override suspend fun loadCongregationEntities(congregations: List<CongregationEntity>) =
        withContext(dispatcher) {
            congregationDao.insert(congregations)
        }

    override suspend fun loadCongregationTotalEntities(congregationTotals: List<CongregationTotalEntity>) =
        withContext(dispatcher) {
            congregationDao.insert(congregationTotals)
        }
}
