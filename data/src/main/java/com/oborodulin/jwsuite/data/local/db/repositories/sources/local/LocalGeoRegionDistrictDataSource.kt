package com.oborodulin.jwsuite.data.local.db.repositories.sources.local

import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionDistrictView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoRegionDistrictDataSource {
    fun getAllDistricts(): Flow<List<GeoRegionDistrictView>>
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
}