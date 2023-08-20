package com.oborodulin.jwsuite.data_geo.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoStreetDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoStreetDataSourceImpl @Inject constructor(
    private val streetDao: GeoStreetDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoStreetDataSource {
    override fun getAllStreets() = streetDao.findAll()
    override fun getLocalityStreets(localityId: UUID, isPrivateSector: Boolean?) =
        streetDao.findByLocalityIdAndPrivateSectorMark(localityId, isPrivateSector)

    override fun getLocalityDistrictStreets(localityDistrictId: UUID, isPrivateSector: Boolean?) =
        streetDao.findByLocalityDistrictIdAndPrivateSectorMark(localityDistrictId, isPrivateSector)

    override fun getMicrodistrictStreets(microdistrictId: UUID, isPrivateSector: Boolean?) =
        streetDao.findByMicrodistrictIdAndPrivateSectorMark(microdistrictId, isPrivateSector)

    override fun getStreet(streetId: UUID) = streetDao.findDistinctById(streetId)
    override suspend fun insertStreet(street: GeoStreetEntity, textContent: GeoStreetTlEntity) =
        withContext(dispatcher) {
            streetDao.insert(street, textContent)
        }

    override suspend fun updateStreet(street: GeoStreetEntity, textContent: GeoStreetTlEntity) =
        withContext(dispatcher) {
            streetDao.update(street, textContent)
        }

    override suspend fun deleteStreet(street: GeoStreetEntity) = withContext(dispatcher) {
        streetDao.delete(street)
    }

    override suspend fun deleteStreetById(streetId: UUID) = withContext(dispatcher) {
        streetDao.deleteById(streetId)
    }

    override suspend fun deleteStreets(streets: List<GeoStreetEntity>) =
        withContext(dispatcher) {
            streetDao.delete(streets)
        }

    override suspend fun deleteAllStreets() = withContext(dispatcher) {
        streetDao.deleteAll()
    }
}