package com.oborodulin.jwsuite.data_geo.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.LocalGeoLocalityDistrictDataSource
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
    override fun getAllLocalityDistricts() = localityDistrictDao.findAll()
    override fun getLocalityDistricts(localityId: UUID) =
        localityDistrictDao.findByLocalityId(localityId)

    override fun getStreetLocalityDistricts(streetId: UUID) =
        localityDistrictDao.findByStreetId(streetId)

    override fun getLocalityDistrictsForStreet(streetId: UUID) =
        localityDistrictDao.findForStreetByStreetId(streetId)

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
}