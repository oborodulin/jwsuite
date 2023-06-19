package com.oborodulin.jwsuite.data.local.db.sources.local

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoDataSource
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

/**
 * Created by o.borodulin on 08.August.2022
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalGeoDataSourceImpl @Inject constructor(
    private val regionDao: GeoRegionDao,
    private val regionDistrictDao: GeoRegionDistrictDao,
    private val localityDao: GeoLocalityDao,
    private val localityDistrictDao: GeoLocalityDistrictDao,
    private val microdistrictDao: GeoMicrodistrictDao,
    private val streetDao: GeoStreetDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : LocalGeoDataSource {
    // Regions:
    override fun getRegions() = regionDao.findDistinctAll()
    override fun getRegion(regionId: UUID) = regionDao.findDistinctById(regionId)
    override fun getFavoriteCongregationRegion() = regionDao.findByFavoriteCongregation()

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

    // RegionDistricts:
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

    // Localities:
    override fun getRegionLocalities(regionId: UUID) = localityDao.findByRegionId(regionId)
    override fun getRegionDistrictLocalities(regionDistrictId: UUID) =
        localityDao.findByRegionDistrictId(regionDistrictId)

    override fun getLocality(localityId: UUID) = localityDao.findDistinctById(localityId)
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

    // Locality Districts:
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

    // Microdistricts:
    override fun getMicrodistricts(localityDistrictId: UUID) =
        microdistrictDao.findByLocalityDistrictId(localityDistrictId)

    override fun getMicrodistrict(microdistrictId: UUID) =
        microdistrictDao.findDistinctById(microdistrictId)

    override suspend fun insertMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    ) = withContext(dispatcher) {
        microdistrictDao.insert(microdistrict, textContent)
    }

    override suspend fun updateMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    ) = withContext(dispatcher) {
        microdistrictDao.update(microdistrict, textContent)
    }

    override suspend fun deleteMicrodistrict(microdistrict: GeoMicrodistrictEntity) =
        withContext(dispatcher) {
            microdistrictDao.delete(microdistrict)
        }

    override suspend fun deleteMicrodistrictById(microdistrictId: UUID) = withContext(dispatcher) {
        microdistrictDao.deleteById(microdistrictId)
    }

    override suspend fun deleteMicrodistricts(microdistricts: List<GeoMicrodistrictEntity>) =
        withContext(dispatcher) {
            microdistrictDao.delete(microdistricts)
        }

    override suspend fun deleteAllMicrodistricts() = withContext(dispatcher) {
        microdistrictDao.deleteAll()
    }

    // MD Streets:
    override suspend fun insertMicrodistrictStreet(
        microdistrict: GeoMicrodistrictEntity, street: GeoStreetEntity
    ) = withContext(dispatcher) {
        microdistrictDao.insert(microdistrict, street)
    }

    override suspend fun updateMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity) =
        withContext(dispatcher) {
            microdistrictDao.update(districtStreet)
        }

    override suspend fun deleteMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity) =
        withContext(dispatcher) {
            microdistrictDao.deleteStreet(districtStreet)
        }

    override suspend fun deleteMicrodistrictStreet(districtStreetId: UUID) =
        withContext(dispatcher) {
            microdistrictDao.deleteStreetById(districtStreetId)
        }

    override suspend fun deleteMicrodistrictStreets(microdistrictId: UUID) =
        withContext(dispatcher) {
            microdistrictDao.deleteStreetsByMicrodistrictId(microdistrictId)
        }

    // Streets:
    override fun getLocalityStreets(localityId: UUID) = streetDao.findByLocalityId(localityId)

    override fun getLocalityDistrictStreets(localityDistrictId: UUID) =
        streetDao.findByLocalityDistrictId(localityDistrictId)

    override fun getMicrodistrictStreets(microdistrictId: UUID) =
        streetDao.findByMicrodistrictId(microdistrictId)

    override fun getTerritoryStreets(territoryId: UUID) = streetDao.findByTerritoryId(territoryId)

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
