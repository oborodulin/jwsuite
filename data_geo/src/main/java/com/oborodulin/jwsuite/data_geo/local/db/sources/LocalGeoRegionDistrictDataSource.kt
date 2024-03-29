package com.oborodulin.jwsuite.data_geo.local.db.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoRegionDistrictDataSource {
    fun getAllDistricts(): Flow<List<RegionDistrictView>>
    fun getRegionDistricts(regionId: UUID): Flow<List<RegionDistrictView>>
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

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getRegionDistrictEntities(): Flow<List<GeoRegionDistrictEntity>>
    fun getRegionDistrictTlEntities(): Flow<List<GeoRegionDistrictTlEntity>>
    suspend fun loadRegionDistrictEntities(regionDistricts: List<GeoRegionDistrictEntity>)
    suspend fun loadRegionDistrictTlEntities(regionDistrictTls: List<GeoRegionDistrictTlEntity>)
}