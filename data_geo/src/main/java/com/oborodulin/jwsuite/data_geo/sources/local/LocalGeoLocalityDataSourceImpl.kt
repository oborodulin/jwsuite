package com.oborodulin.jwsuite.data_geo.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoLocalityDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoLocalityDataSourceImpl @Inject constructor(
    private val localityDao: GeoLocalityDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoLocalityDataSource {
    override fun getAllLocalities() = localityDao.findDistinctAll()
    override fun getRegionLocalities(regionId: UUID) = localityDao.findDistinctByRegionId(regionId)
    override fun getRegionDistrictLocalities(regionDistrictId: UUID) =
        localityDao.findDistinctByRegionDistrictId(regionDistrictId)

    override fun getLocality(localityId: UUID) = localityDao.findById(localityId)
    override suspend fun insertLocality(
        locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity
    ) = withContext(dispatcher) { localityDao.insert(locality, textContent) }

    override suspend fun updateLocality(
        locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity
    ) = withContext(dispatcher) { localityDao.update(locality, textContent) }

    override suspend fun deleteLocality(locality: GeoLocalityEntity) = withContext(dispatcher) {
        localityDao.delete(locality)
    }

    override suspend fun deleteLocalityById(localityId: UUID) = withContext(dispatcher) {
        localityDao.deleteById(localityId)
    }

    override suspend fun deleteLocalities(localities: List<GeoLocalityEntity>) =
        withContext(dispatcher) {
            localityDao.delete(localities)
        }

    override suspend fun deleteAllLocalities() = withContext(dispatcher) {
        localityDao.deleteAll()
    }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getLocalityEntities() = localityDao.selectEntities()
    override fun getLocalityTlEntities() = localityDao.selectTlEntities()
    override suspend fun loadLocalityEntities(localities: List<GeoLocalityEntity>) =
        withContext(dispatcher) {
            localityDao.insert(localities)
        }

    override suspend fun loadLocalityTlEntities(localityTls: List<GeoLocalityTlEntity>) =
        withContext(dispatcher) {
            localityDao.insertTls(localityTls)
        }
}
