package com.oborodulin.jwsuite.data_congregation.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalCongregationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
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
    override fun getFavoriteCongregationTotals() = congregationDao.findDistinctfindTotals()
    override suspend fun insertCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.insertWithFavorite(congregation)
        }

    override suspend fun updateCongregation(congregation: CongregationEntity) =
        withContext(dispatcher) {
            congregationDao.updateWithFavorite(congregation)
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
}