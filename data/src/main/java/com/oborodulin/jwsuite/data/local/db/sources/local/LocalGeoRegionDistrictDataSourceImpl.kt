package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoRegionDistrictDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoRegionDistrictDataSourceImpl @Inject constructor(
    private val regionDistrictDao: GeoRegionDistrictDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoRegionDistrictDataSource {
    override fun getRegionDistricts(regionId: UUID) = regionDistrictDao.findByRegionId(regionId)
    override fun getRegionDistrict(regionDistrictId: UUID) =
        regionDistrictDao.findDistinctById(regionDistrictId)

    override suspend fun insertRegionDistrict(
        regionDistrict: GeoRegionDistrictEntity, textContent: GeoRegionDistrictTlEntity
    ) = withContext(dispatcher) {
        regionDistrictDao.insert(regionDistrict, textContent)
    }

    override suspend fun updateRegionDistrict(
        regionDistrict: GeoRegionDistrictEntity, textContent: GeoRegionDistrictTlEntity
    ) = withContext(dispatcher) {
        regionDistrictDao.update(regionDistrict, textContent)
    }

    override suspend fun deleteRegionDistrict(regionDistrict: GeoRegionDistrictEntity) =
        withContext(dispatcher) {
            regionDistrictDao.delete(regionDistrict)
        }

    override suspend fun deleteRegionDistrictById(regionDistrictId: UUID) =
        withContext(dispatcher) {
            regionDistrictDao.deleteById(regionDistrictId)
        }

    override suspend fun deleteRegionDistricts(regionDistricts: List<GeoRegionDistrictEntity>) =
        withContext(dispatcher) {
            regionDistrictDao.delete(regionDistricts)
        }

    override suspend fun deleteAllRegionDistricts() = withContext(dispatcher) {
        regionDistrictDao.deleteAll()
    }
}
