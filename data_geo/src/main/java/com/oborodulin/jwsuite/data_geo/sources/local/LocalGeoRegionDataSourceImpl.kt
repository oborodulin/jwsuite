package com.oborodulin.jwsuite.data_geo.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoRegionDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoRegionDataSourceImpl @Inject constructor(
    private val regionDao: GeoRegionDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoRegionDataSource {
    override fun getRegions() = regionDao.findDistinctAll()
    override fun getRegion(regionId: UUID) = regionDao.findDistinctById(regionId)
    //override fun getFavoriteCongregationRegion() = regionDao.findByFavoriteCongregation()

    override suspend fun insertRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity) =
        withContext(dispatcher) {
            regionDao.insert(region, textContent)
        }

    override suspend fun updateRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity) =
        withContext(dispatcher) {
            regionDao.update(region, textContent)
        }

    override suspend fun deleteRegion(region: GeoRegionEntity) = withContext(dispatcher) {
        regionDao.delete(region)
    }

    override suspend fun deleteRegionById(regionId: UUID) = withContext(dispatcher) {
        regionDao.deleteById(regionId)
    }

    override suspend fun deleteRegions(regions: List<GeoRegionEntity>) =
        withContext(dispatcher) {
            regionDao.delete(regions)
        }

    override suspend fun deleteAllRegions() = withContext(dispatcher) {
        regionDao.deleteAll()
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getRegionEntities() = regionDao.selectEntities()
    override fun getRegionTlEntities() = regionDao.selectTlEntities()
    override suspend fun loadRegionEntities(regions: List<GeoRegionEntity>) =
        withContext(dispatcher) {
            regionDao.insert(regions)
        }

    override suspend fun loadRegionTlEntities(regionTls: List<GeoRegionTlEntity>) =
        withContext(dispatcher) {
            regionDao.insertTls(regionTls)
        }
}
