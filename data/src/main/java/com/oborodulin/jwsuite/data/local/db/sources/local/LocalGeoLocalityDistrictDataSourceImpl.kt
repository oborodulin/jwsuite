package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoLocalityDistrictDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoLocalityDistrictDataSourceImpl @Inject constructor(
    private val localityDistrictDao: GeoLocalityDistrictDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoLocalityDistrictDataSource {
    override fun getLocalityDistricts(localityId: UUID) =
        localityDistrictDao.findByLocalityId(localityId)

    override fun getLocalityDistrict(localityDistrictId: UUID) =
        localityDistrictDao.findDistinctById(localityDistrictId)

    override suspend fun insertLocalityDistrict(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    ) = withContext(dispatcher) {
        localityDistrictDao.insert(localityDistrict, textContent)
    }

    override suspend fun updateLocalityDistrict(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    ) = withContext(dispatcher) {
        localityDistrictDao.update(localityDistrict, textContent)
    }

    override suspend fun deleteLocalityDistrict(localityDistrict: GeoLocalityDistrictEntity) =
        withContext(dispatcher) {
            localityDistrictDao.delete(localityDistrict)
        }

    override suspend fun deleteLocalityDistrictById(localityDistrictId: UUID) =
        withContext(dispatcher) {
            localityDistrictDao.deleteById(localityDistrictId)
        }

    override suspend fun deleteLocalityDistricts(localityDistricts: List<GeoLocalityDistrictEntity>) =
        withContext(dispatcher) {
            localityDistrictDao.delete(localityDistricts)
        }

    override suspend fun deleteAllLocalityDistricts() = withContext(dispatcher) {
        localityDistrictDao.deleteAll()
    }

    // LD Streets:
    override suspend fun insertLocalityDistrictStreet(
        localityDistrict: GeoLocalityDistrictEntity, street: GeoStreetEntity
    ) = withContext(dispatcher) {
        localityDistrictDao.insert(localityDistrict, street)
    }

    override suspend fun updateLocalityDistrictStreet(districtStreet: GeoDistrictStreetEntity) =
        withContext(dispatcher) {
            localityDistrictDao.update(districtStreet)
        }

    override suspend fun deleteLocalityDistrictStreet(districtStreet: GeoDistrictStreetEntity) =
        withContext(dispatcher) {
            localityDistrictDao.deleteStreet(districtStreet)
        }

    override suspend fun deleteLocalityDistrictStreet(districtStreetId: UUID) =
        withContext(dispatcher) {
            localityDistrictDao.deleteStreetById(districtStreetId)
        }

    override suspend fun deleteLocalityDistrictStreets(localityDistrictId: UUID) =
        withContext(dispatcher) {
            localityDistrictDao.deleteStreetsByLocalityDistrictId(localityDistrictId)
        }
}
