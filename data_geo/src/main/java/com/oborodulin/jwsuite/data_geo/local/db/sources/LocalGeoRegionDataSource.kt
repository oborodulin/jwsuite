package com.oborodulin.jwsuite.data_geo.local.db.sources

import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalGeoRegionDataSource {
    fun getRegions(): Flow<List<RegionView>>
    fun getCountryRegions(countryId: UUID): Flow<List<RegionView>>
    fun getRegion(regionId: UUID): Flow<GeoRegionView>

    //fun getFavoriteCongregationRegion(): Flow<GeoRegionView>
    suspend fun insertRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity)
    suspend fun updateRegion(region: GeoRegionEntity, textContent: GeoRegionTlEntity)
    suspend fun deleteRegion(region: GeoRegionEntity)
    suspend fun deleteRegionById(regionId: UUID)
    suspend fun deleteRegions(regions: List<GeoRegionEntity>)
    suspend fun deleteAllRegions()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun getRegionEntities(): Flow<List<GeoRegionEntity>>
    fun getRegionTlEntities(): Flow<List<GeoRegionTlEntity>>
    suspend fun loadRegionEntities(regions: List<GeoRegionEntity>)
    suspend fun loadRegionTlEntities(regionTls: List<GeoRegionTlEntity>)
}