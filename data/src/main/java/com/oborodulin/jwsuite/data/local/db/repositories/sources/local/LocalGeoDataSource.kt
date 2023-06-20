package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

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
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.data.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.data.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoDataSource {
    // Regions:
    fun getRegions(): Flow<List<GeoRegionView>>
    fun getRegion(regionId: UUID): Flow<GeoRegionView>
    fun getFavoriteCongregationRegion(): Flow<GeoRegionView>
    suspend fun insertRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity)
    suspend fun updateRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity)
    suspend fun deleteRegion(region: GeoRegionEntity)
    suspend fun deleteRegionById(regionId: UUID)
    suspend fun deleteRegions(regions: List<GeoRegionEntity>)
    suspend fun deleteAllRegions()

    // RegionDistricts:
    fun getRegionDistricts(regionId: UUID): Flow<List<GeoRegionDistrictView>>
    fun getRegionDistrict(regionDistrictId: UUID): Flow<GeoRegionDistrictView>
    suspend fun insertRegionDistrict(
        regionDistrict: GeoRegionDistrictEntity, textContent: GeoRegionDistrictTlEntity
    )

    suspend fun updateRegionDistrict(
        regionDistrict: GeoRegionDistrictEntity, textContent: GeoRegionDistrictTlEntity
    )

    suspend fun deleteRegionDistrict(regionDistrict: GeoRegionDistrictEntity)
    suspend fun deleteRegionDistrictById(regionDistrictId: UUID)
    suspend fun deleteRegionDistricts(regionDistricts: List<GeoRegionDistrictEntity>)
    suspend fun deleteAllRegionDistricts()

    // Localities:
    fun getRegionLocalities(regionId: UUID): Flow<List<GeoLocalityView>>
    fun getRegionDistrictLocalities(regionDistrictId: UUID): Flow<List<GeoLocalityView>>
    fun getLocality(localityId: UUID): Flow<GeoLocalityView>
    suspend fun insertLocality(locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity)
    suspend fun updateLocality(locality: GeoLocalityEntity, textContent: GeoLocalityTlEntity)
    suspend fun deleteLocality(locality: GeoLocalityEntity)
    suspend fun deleteLocalityById(localityId: UUID)
    suspend fun deleteLocalities(localities: List<GeoLocalityEntity>)
    suspend fun deleteAllLocalities()

    // Locality Districts:
    fun getLocalityDistricts(localityId: UUID): Flow<List<GeoLocalityDistrictView>>
    fun getLocalityDistrict(localityDistrictId: UUID): Flow<GeoLocalityDistrictView>
    suspend fun insertLocalityDistrict(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    )

    suspend fun updateLocalityDistrict(
        localityDistrict: GeoLocalityDistrictEntity, textContent: GeoLocalityDistrictTlEntity
    )

    suspend fun deleteLocalityDistrict(localityDistrict: GeoLocalityDistrictEntity)
    suspend fun deleteLocalityDistrictById(localityDistrictId: UUID)
    suspend fun deleteLocalityDistricts(localityDistricts: List<GeoLocalityDistrictEntity>)
    suspend fun deleteAllLocalityDistricts()

    // LD Streets:
    suspend fun insertLocalityDistrictStreet(
        localityDistrict: GeoLocalityDistrictEntity, street: GeoStreetEntity
    )

    suspend fun updateLocalityDistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteLocalityDistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteLocalityDistrictStreet(districtStreetId: UUID)
    suspend fun deleteLocalityDistrictStreets(localityDistrictId: UUID)

    // Microdistricts:
    fun getMicrodistricts(localityDistrictId: UUID): Flow<List<GeoMicrodistrictView>>
    fun getMicrodistrict(microdistrictId: UUID): Flow<GeoMicrodistrictView>
    suspend fun insertMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    )

    suspend fun updateMicrodistrict(
        microdistrict: GeoMicrodistrictEntity, textContent: GeoMicrodistrictTlEntity
    )

    suspend fun deleteMicrodistrict(microdistrict: GeoMicrodistrictEntity)
    suspend fun deleteMicrodistrictById(microdistrictId: UUID)
    suspend fun deleteMicrodistricts(microdistricts: List<GeoMicrodistrictEntity>)
    suspend fun deleteAllMicrodistricts()

    // MD Streets:
    suspend fun insertMicrodistrictStreet(
        microdistrict: GeoMicrodistrictEntity, street: GeoStreetEntity
    )

    suspend fun updateMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteMicrodistrictStreet(districtStreet: GeoDistrictStreetEntity)
    suspend fun deleteMicrodistrictStreet(districtStreetId: UUID)
    suspend fun deleteMicrodistrictStreets(microdistrictId: UUID)
}