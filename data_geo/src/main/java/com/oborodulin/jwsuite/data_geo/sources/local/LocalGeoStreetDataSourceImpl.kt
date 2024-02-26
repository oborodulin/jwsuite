package com.oborodulin.jwsuite.data_geo.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.sources.LocalGeoStreetDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoStreetDataSourceImpl @Inject constructor(
    private val streetDao: GeoStreetDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoStreetDataSource {
    override fun getAllStreets() = streetDao.findDistinctAll()
    override fun getLocalityStreets(localityId: UUID, isPrivateSector: Boolean?) =
        streetDao.findDistinctByLocalityIdAndPrivateSectorMark(localityId, isPrivateSector)

    override fun getLocalityDistrictStreets(localityDistrictId: UUID, isPrivateSector: Boolean?) =
        streetDao.findDistinctByLocalityDistrictIdAndPrivateSectorMark(
            localityDistrictId, isPrivateSector
        )

    override fun getMicrodistrictStreets(microdistrictId: UUID, isPrivateSector: Boolean?) =
        streetDao.findDistinctByMicrodistrictIdAndPrivateSectorMark(
            microdistrictId, isPrivateSector
        )

    override fun getStreetsForTerritory(
        localityId: UUID, localityDistrictId: UUID?, microdistrictId: UUID?, excludes: List<UUID>
    ) = streetDao.findDistinctByLocalityIdAndLocalityDistrictIdAndMicrodistrictIdWithExcludes(
        localityId, localityDistrictId, microdistrictId, excludes
    )

    override fun getStreet(streetId: UUID) = streetDao.findById(streetId)
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

    // Districts:
    override suspend fun updateStreetDistrict(streetDistrict: GeoStreetDistrictEntity) =
        withContext(dispatcher) {
            streetDao.update(streetDistrict)
        }

    override suspend fun deleteStreetDistrict(streetDistrict: GeoStreetDistrictEntity) =
        withContext(dispatcher) {
            streetDao.deleteStreetDistrict(streetDistrict)
        }

    override suspend fun deleteStreetDistrict(streetDistrictId: UUID) =
        withContext(dispatcher) { streetDao.deleteStreetDistrictById(streetDistrictId) }

    // Locality Districts:
    override suspend fun insertStreetLocalityDistrict(
        street: GeoStreetEntity, localityDistrict: GeoLocalityDistrictEntity
    ) = withContext(dispatcher) { streetDao.insert(street, localityDistrict) }

    override suspend fun insertStreetLocalityDistrict(streetId: UUID, localityDistrictId: UUID) =
        withContext(dispatcher) {
            streetDao.insertStreetLocalityDistrict(streetId, localityDistrictId)
        }

    override suspend fun deleteStreetLocalityDistrict(streetId: UUID, localityDistrictId: UUID) =
        withContext(dispatcher) {
            streetDao.deleteStreetDistrictByLocalityDistrictId(streetId, localityDistrictId)
        }

    // Microdistricts:
    override suspend fun insertStreetMicrodistrict(
        street: GeoStreetEntity, microdistrict: GeoMicrodistrictEntity
    ) = withContext(dispatcher) { streetDao.insert(street, microdistrict) }

    override suspend fun insertStreetMicrodistrict(
        streetId: UUID, localityDistrictId: UUID, microdistrictId: UUID
    ) = withContext(dispatcher) {
        streetDao.insertStreetMicrodistrict(streetId, localityDistrictId, microdistrictId)
    }

    override suspend fun deleteStreetMicrodistrict(streetId: UUID, microdistrictId: UUID) =
        withContext(dispatcher) {
            streetDao.deleteStreetDistrictByMicrodistrictId(streetId, microdistrictId)
        }

    // -------------------------------------- CSV Transfer --------------------------------------
    override fun getStreetEntities() = streetDao.selectEntities()
    override fun getStreetTlEntities() = streetDao.selectTlEntities()
    override suspend fun loadStreetEntities(streets: List<GeoStreetEntity>) =
        withContext(dispatcher) {
            streetDao.insert(streets)
        }

    override suspend fun loadStreetTlEntities(streetTls: List<GeoStreetTlEntity>) =
        withContext(dispatcher) {
            streetDao.insertTls(streetTls)
        }
}